package com.c104.seolo.domain.user.controller;

import com.c104.seolo.domain.user.dto.request.UserJoinRequest;
import com.c104.seolo.domain.user.dto.response.UserJoinResponse;
import com.c104.seolo.domain.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
