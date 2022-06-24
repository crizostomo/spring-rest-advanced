package com.developer.beverageapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstController {

    @GetMapping("/hi")
    @ResponseBody
    public String hi(){
        return "hi";
    }
}
