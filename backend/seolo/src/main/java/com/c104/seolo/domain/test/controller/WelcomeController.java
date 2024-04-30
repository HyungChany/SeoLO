package com.c104.seolo.domain.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

    @GetMapping("/api/test")
    @ResponseBody
    public String sayHello() {
        return "Welcome";
    }
}
