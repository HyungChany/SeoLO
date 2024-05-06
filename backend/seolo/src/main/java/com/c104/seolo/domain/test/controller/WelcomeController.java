package com.c104.seolo.domain.test.controller;

import com.c104.seolo.domain.core.dto.request.LockerEnrollRequest;
import com.c104.seolo.domain.core.entity.Token;
import com.c104.seolo.domain.core.service.CoreTokenService;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import com.c104.seolo.global.security.service.DBUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class WelcomeController {
    private final CoreTokenService coreTokenService;
    private final DBUserDetailService dbUserDetailService;

    @GetMapping("/test")
    @ResponseBody
    public String sayHello() {
        return "Welcome";
    }

    @PostMapping("/core-token")
    @ResponseBody
    public Token issuTokenTest(@AuthenticationPrincipal CCodePrincipal cCodePrincipal, @RequestBody LockerEnrollRequest lockerEnrollRequest) {
        AppUser appUser = dbUserDetailService.loadUserById(cCodePrincipal.getId());
        return coreTokenService.issueCoreAuthToken(appUser, lockerEnrollRequest.getLockerUid());
    }
}
