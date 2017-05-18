package com.netcracker.crm.controller.base;

import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import com.netcracker.crm.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author Karpunets
 * @since 25.04.2017
 */

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Map<String, Object> model, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user = (UserDetailsImpl) principal;
        model.put("user", user);
        return "main";
    }

}