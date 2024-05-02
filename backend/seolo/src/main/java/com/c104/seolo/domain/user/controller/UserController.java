package com.c104.seolo.domain.user.controller;

import com.c104.seolo.domain.user.dto.request.UserJoinRequest;
import com.c104.seolo.domain.user.dto.request.UserPwdResetRequest;
import com.c104.seolo.domain.user.dto.response.UserInfoResponse;
import com.c104.seolo.domain.user.dto.response.UserJoinResponse;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.domain.user.service.UserService;
import com.c104.seolo.global.security.dto.request.PINLoginRequest;
import com.c104.seolo.global.security.dto.request.PINResetRequest;
import com.c104.seolo.global.security.dto.response.PINLoginResponse;
import com.c104.seolo.global.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/join")
    public UserJoinResponse joinUser(@Valid @RequestBody UserJoinRequest userJoinRequest) {
        return userService.registUser(userJoinRequest);
    }

//    @Secured("ROLE_MANAGER")
    @GetMapping("/users/profile")
    public UserInfoResponse getUserInfo(@AuthenticationPrincipal AppUser user) {
        log.debug("현재 로그인 유저의 authentication : {}", SecurityContextHolder.getContext().getAuthentication());
        return userService.getUserInfo(user);
    }

    @PatchMapping("/users/pwd")
    public void changeUserPwd(@AuthenticationPrincipal AppUser user, @Valid @RequestBody UserPwdResetRequest userPwdResetRequest) {
        log.debug("{}",userPwdResetRequest.getNewPassword());
        log.debug("{}", userPwdResetRequest.getCheckNewPassword());
        userService.resetUserPassword(user, userPwdResetRequest);
    }

    @PostMapping("/users/pin")
    public PINLoginResponse authPin(@AuthenticationPrincipal AppUser appUser, @Valid @RequestBody PINLoginRequest pinLoginRequest) {
        return authService.pinLogin(appUser, pinLoginRequest);
    }

    @PatchMapping("/users/pin")
    public String changeUserPin(@AuthenticationPrincipal AppUser appUser, @Valid @RequestBody PINResetRequest pinResetRequest) {
        authService.resetPin(appUser, pinResetRequest);
        return "PIN 변경 성공 로그아웃 시켜주세요";
    }

}
