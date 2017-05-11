package com.netcracker.crm.service.impl;

import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.exception.NoSuchEmailException;
import com.netcracker.crm.service.email.EmailParam;
import com.netcracker.crm.service.email.EmailParamKeys;
import com.netcracker.crm.service.email.EmailType;
import com.netcracker.crm.service.email.senders.RecoveryPasswordSender;
import com.netcracker.crm.service.security.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * Created by Pasha on 26.04.2017.
 */
@Service
public class ForgotPasswordService {

    @Autowired
    private RecoveryPasswordSender recoveryPasswordSender;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final int PASSWORD_LENGTH = 10;

    public User checkEmailAndPhone(String email, String phone) throws NoSuchEmailException {
        User user = ifExistEmail(email);
        if (user.getPhone().equals(phone)) {
            return user;
        }
        throw new NoSuchEmailException("Not the right phone for this email : " + email);
    }


    private User ifExistEmail(String email) throws NoSuchEmailException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new NoSuchEmailException("User with email " + email + " is not found");
        }
        return user;
    }


    public void changePassword(User user) throws MessagingException {
        String password = generatePassword(user);
        replacePassword(user, passwordEncoder.encode(password));
        recoveryPasswordSender.send(fillMap(user, password));
    }

    private void replacePassword(User user, String password) {
        userDao.updatePassword(user, password);
    }

    private String generatePassword(User user) {
        RandomString randomString = new RandomString(PASSWORD_LENGTH);
        String password;
        while (true) {
            password = randomString.nextString();
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return password;
            }
        }
    }

    private EmailParam fillMap(User user, String password) {
        EmailParam emailMap = new EmailParam(EmailType.RECOVERY_PASSWORD);
        emailMap.put(EmailParamKeys.USER, user);
        emailMap.put(EmailParamKeys.USER_PASSWORD, password);
        return emailMap;
    }
}
