package com.c104.seolo.domain.user.controller;

import com.c104.seolo.domain.user.dto.request.UserJoinRequest;
import com.c104.seolo.domain.user.dto.response.UserJoinResponse;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public UserJoinResponse joinUser(@Valid @RequestBody UserJoinRequest userJoinRequest) {
        return userService.registUser(userJoinRequest);
    }

    @GetMapping("/test/users/profile")
    public AppUser getUserInfo(@AuthenticationPrincipal AppUser user) {
        log.info("현재 로그인 유저의 context : {}", SecurityContextHolder.getContext());
        log.info("현재 로그인 유저의 authentication : {}", SecurityContextHolder.getContext().getAuthentication());
        log.info("현재 로그인 유저의 Principal : {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.info("{}",user);
        return user;
    }
}
