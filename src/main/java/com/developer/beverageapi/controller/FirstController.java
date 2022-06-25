package com.developer.beverageapi.controller;

import com.developer.beverageapi.model.Client;
import com.developer.beverageapi.service.ClientActivationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstController {

    private ClientActivationService activationService;

    public FirstController(ClientActivationService activationService) {
        this.activationService = activationService;
    }

    @GetMapping("/hi")
    @ResponseBody
    public String hi(){
        Client client = new Client("John", "john@gmail.com", "551199663322");

        activationService.active(client);

        return "hi";
    }
}
