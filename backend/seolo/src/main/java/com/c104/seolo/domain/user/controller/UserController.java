package com.c104.seolo.domain.user.controller;

import com.c104.seolo.domain.user.dto.request.UserJoinRequest;
import com.c104.seolo.domain.user.dto.request.UserLoginRequest;
import com.c104.seolo.domain.user.dto.request.UserPwdCheckRequest;
import com.c104.seolo.domain.user.dto.request.UserPwdResetRequest;
import com.c104.seolo.domain.user.dto.response.UserInfoResponse;
import com.c104.seolo.domain.user.dto.response.UserJoinResponse;
import com.c104.seolo.domain.user.dto.response.UserListResponse;
import com.c104.seolo.domain.user.dto.response.UserLoginResponse;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.domain.user.service.UserService;
import com.c104.seolo.global.security.dto.request.PINLoginRequest;
import com.c104.seolo.global.security.dto.request.PINResetRequest;
import com.c104.seolo.global.security.dto.response.JwtLoginSuccessResponse;
import com.c104.seolo.global.security.dto.response.PINLoginResponse;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import com.c104.seolo.global.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // JWT 토큰 전용 로그인
    @PostMapping("/login")
    public JwtLoginSuccessResponse userLogin(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return authService.userLogin(userLoginRequest);
    }

    @PostMapping("/logout")
    public void userLogout(@AuthenticationPrincipal CCodePrincipal cCodePrincipal) {
        authService.userLogout(cCodePrincipal);
    }

//    @Secured("ROLE_MANAGER")
    @GetMapping("/users/profile")
    public UserInfoResponse getUserInfo(@AuthenticationPrincipal CCodePrincipal cCodePrincipal) {
        log.debug("현재 로그인 유저의 authentication : {}", SecurityContextHolder.getContext().getAuthentication());
        return userService.getUserInfo(cCodePrincipal);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/pwd")
    public void checkSameUserPwd(@AuthenticationPrincipal CCodePrincipal cCodePrincipal, @Valid @RequestBody UserPwdCheckRequest userPwdCheckRequest) {
        userService.checkSamePassword(cCodePrincipal, userPwdCheckRequest);
    }

    @PatchMapping("/users/pwd")
    public void changeUserPwd(@AuthenticationPrincipal CCodePrincipal cCodePrincipal, @Valid @RequestBody UserPwdResetRequest userPwdResetRequest) {
        log.debug("{}",userPwdResetRequest.getNewPassword());
        log.debug("{}", userPwdResetRequest.getCheckNewPassword());
        userService.resetUserPassword(cCodePrincipal, userPwdResetRequest);
    }

    @PostMapping("/users/pin")
    public PINLoginResponse authPin(@AuthenticationPrincipal CCodePrincipal cCodePrincipal, @Valid @RequestBody PINLoginRequest pinLoginRequest) {
        return authService.pinLogin(cCodePrincipal, pinLoginRequest);
    }

    @PatchMapping("/users/pin")
    public String changeUserPin(@AuthenticationPrincipal CCodePrincipal cCodePrincipal, @Valid @RequestBody PINResetRequest pinResetRequest) {
        authService.resetPin(cCodePrincipal, pinResetRequest);
        return "PIN 변경 성공 로그아웃 시켜주세요";
    }


    @GetMapping("/users")
    public ResponseEntity<UserListResponse> getAllUsers(
            @RequestHeader("Company-Code") String companyCode
    ) {
        return ResponseEntity.ok(userService.getUserList(companyCode));
    }
}
