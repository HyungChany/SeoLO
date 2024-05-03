package com.c104.seolo.global.security.service;

import com.c104.seolo.domain.user.dto.request.UserLoginRequest;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.security.dto.request.PINLoginRequest;
import com.c104.seolo.global.security.dto.request.PINResetRequest;
import com.c104.seolo.global.security.dto.response.JwtLoginSuccessResponse;
import com.c104.seolo.global.security.dto.response.PINLoginResponse;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;

public interface AuthService {
    JwtLoginSuccessResponse userLogin(UserLoginRequest userLoginRequest);
    void userLogout(CCodePrincipal cCodePrincipal);
    PINLoginResponse pinLogin(CCodePrincipal cCodePrincipal, PINLoginRequest pinLoginRequest);
    void resetPin(CCodePrincipal cCodePrincipal, PINResetRequest pinResetRequest);
}
