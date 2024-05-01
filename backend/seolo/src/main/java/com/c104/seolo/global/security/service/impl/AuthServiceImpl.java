package com.c104.seolo.global.security.service.impl;

import com.c104.seolo.global.security.dto.request.PINLoginRequest;
import com.c104.seolo.global.security.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public void pinLogin(PINLoginRequest pinLoginRequest) {
        // 1. PIN번호도 암호화필요?

    }
}
