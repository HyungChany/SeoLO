package com.c104.seolo.domain.test.controller;

import com.c104.seolo.domain.core.dto.request.LockerEnrollRequest;
import com.c104.seolo.domain.core.entity.Token;
import com.c104.seolo.domain.core.service.CoreTokenService;
import com.c104.seolo.domain.task.dto.request.TaskHistoryAddRequest;
import com.c104.seolo.domain.task.service.TaskHistoryService;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import com.c104.seolo.global.security.service.DBUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class WelcomeController {
    private final CoreTokenService coreTokenService;
    private final DBUserDetailService dbUserDetailService;
    private final TaskHistoryService taskHistoryService;

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

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/enroll-task-history")
    @ResponseBody
    public void enrollHistoryTest(@AuthenticationPrincipal CCodePrincipal cCodePrincipal, @RequestBody TaskHistoryAddRequest taskHistoryAddRequest) {
        taskHistoryService.enrollTaskHistory(cCodePrincipal, taskHistoryAddRequest.getTaskTemplateId(),
                taskHistoryAddRequest.getMachineId(),
                taskHistoryAddRequest.getEndTime(),
                taskHistoryAddRequest.getTaskPrecaution());
    }
}
