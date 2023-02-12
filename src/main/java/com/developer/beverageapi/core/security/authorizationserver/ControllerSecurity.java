package com.developer.beverageapi.core.security.authorizationserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerSecurity {

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/oauth/confirm_access") //WhitelabelApprovalEndpoint.class
    public String approval() {
        return "pages/approval";
    }
}
