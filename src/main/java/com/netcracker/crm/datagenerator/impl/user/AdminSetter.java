package com.netcracker.crm.datagenerator.impl.user;

import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import org.springframework.stereotype.Service;

/**
 * Created by Pasha on 05.05.2017.
 */
@Service
public class AdminSetter extends AbstractUserSetter {
    @Override
    protected UserRole getRole() {
        return UserRole.ROLE_ADMIN;
    }

    @Override
    protected String getEmail(int counter) {
        return "admin" + counter + "@gmail.com";
    }

    @Override
    protected void setOrganization(User user) {
//    TODO nothing
    }

    @Override
    protected void setAddress(User user) {

    }

    @Override
    protected void setContactPerson(User user, int counter) {
//      TODO nothing
    }
}
