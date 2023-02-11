package com.developer.beverageapi.core.security.authorizationserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerSecurity {

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    //http://localhost:8080/oauth/authorize?response_type=code&client_id=beverage-analytics&state=abc&redirect_uri=http://www.beverage-analytics.local:8082&response_type=code

    @GetMapping("/oauth/confirm_access") //WhitelabelApprovalEndpoint.class
    public String approval() {
        return "pages/approval";
    }
}
