package com.c104.seolo.global.security.service;

import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.security.dto.request.PINLoginRequest;
import com.c104.seolo.global.security.dto.request.PINResetRequest;
import com.c104.seolo.global.security.dto.response.PINLoginResponse;

public interface AuthService {
    PINLoginResponse pinLogin(AppUser appUser, PINLoginRequest pinLoginRequest);
    void resetPin(AppUser appUser, PINResetRequest pinResetRequest);
}
