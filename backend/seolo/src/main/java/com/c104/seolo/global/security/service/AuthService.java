package com.c104.seolo.global.security.service;

import com.c104.seolo.global.security.dto.request.PINLoginRequest;

public interface AuthService {
    void pinLogin(PINLoginRequest pinLoginRequest);
}
