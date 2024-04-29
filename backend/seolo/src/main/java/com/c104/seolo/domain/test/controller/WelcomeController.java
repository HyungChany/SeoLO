package com.c104.seolo.domain.test.controller;

import com.c104.seolo.domain.facility.dto.response.FacilityResponse;
import com.c104.seolo.domain.facility.service.FacilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class WelcomeController {

    @GetMapping
    @ResponseBody
    public String sayHello() {
        return "Welcome";
    }
}
