package com.c104.seolo.domain.core.controller;

import com.c104.seolo.domain.user.entity.AppUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/core")
public class CoreAuthController {

    @PostMapping("/{code}")
    public void coreAuthentication(
            @AuthenticationPrincipal AppUser appUser,
            @RequestHeader("Company-Code") String companyCode,
            @PathVariable String code
    ) {

    }
}