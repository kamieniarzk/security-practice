package com.example.securitypractice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/accounts")
    public String accounts() {
        return "accounts";
    }
}
