package com.c104.seolo.domain.test.controller;

import com.c104.seolo.domain.facility.dto.response.FacilityResponse;
import com.c104.seolo.domain.facility.service.FacilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
=======
>>>>>>> 56b3a470655d13d0946dcdfea47284bc70bb1293
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

    @GetMapping("/test")
    @ResponseBody
    public String sayHello() {
        return "Welcome";
    }
}
