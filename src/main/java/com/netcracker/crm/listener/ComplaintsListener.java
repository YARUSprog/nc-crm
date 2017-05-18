package com.netcracker.crm.listener;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.dao.HistoryDao;
import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.ComplaintStatus;
import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.listener.event.ChangeStatusComplaintEvent;
import com.netcracker.crm.listener.event.CreateComplaintEvent;
import com.netcracker.crm.service.email.AbstractEmailSender;
import com.netcracker.crm.service.email.EmailParam;
import com.netcracker.crm.service.email.EmailParamKeys;
import com.netcracker.crm.service.email.EmailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 13.05.2017
 */

@Component
public class ComplaintsListener {

    private HistoryDao historyDao;
    private AbstractEmailSender emailSender;
    private ComplaintDao complaintDao;

    @Autowired
    public ComplaintsListener(HistoryDao historyDao, ComplaintDao complaintDao,
                              @Qualifier("complaintSender") AbstractEmailSender emailSender) {
        this.historyDao = historyDao;
        this.complaintDao = complaintDao;
        this.emailSender = emailSender;
    }

    @EventListener
    public void createComplaint(CreateComplaintEvent event) {
        Complaint complaint = event.getComplaint();
        History history = generateHistory(complaint);
        String role = getRole(complaint.getCustomer());
        history.setDescChangeStatus(role + " with id " +
                complaint.getCustomer().getId() + " created complaint");
        historyDao.create(history);
        sendMail(complaint);
    }

    @EventListener(condition = "#event.complaint.status.name.equals('OPEN') " +
            "&& #event.done==false")
    public void acceptComplaint(ChangeStatusComplaintEvent event) {
        Complaint complaint = event.getComplaint();
        complaint.setStatus(ComplaintStatus.SOLVING);
        History history = generateHistory(complaint);
        String role = getRole(complaint.getPmg());
        history.setDescChangeStatus(role + " with id " +
                complaint.getPmg().getId() + " accepted complaint");
        saveStatusAndHistory(complaint, history);
        event.setDone(true);
    }

    @EventListener(condition = "#event.complaint.status.name.equals('SOLVING') " +
            "&& #event.done==false")
    public void closeComplaint(ChangeStatusComplaintEvent event) {
        Complaint complaint = event.getComplaint();
        complaint.setStatus(ComplaintStatus.CLOSED);
        History history = generateHistory(complaint);
        String role = getRole(complaint.getPmg());
        history.setDescChangeStatus(role + " with id " +
                complaint.getPmg().getId() + " closed complaint");
        history.setComplaint(complaint);
        saveStatusAndHistory(complaint, history);
        event.setDone(true);
    }

    private void sendMail(Complaint complaint) {
        EmailParam emailMap = new EmailParam(EmailType.COMPLAINT);
        emailMap.put(EmailParamKeys.COMPLAINT, complaint);
        try {
            emailSender.send(emailMap);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getRole(User user) {
        String role = user.getUserRole().getName();
        return role.substring(role.indexOf("_") + 1);
    }

    private History generateHistory(Complaint complaint) {
        History history = new History();
        history.setDateChangeStatus(LocalDateTime.now());
        history.setNewStatus(complaint.getStatus());
        history.setComplaint(complaint);
        return history;
    }

    private void saveStatusAndHistory(Complaint complaint, History history) {
        complaintDao.update(complaint);
        historyDao.create(history);
        sendMail(complaint);
    }
}