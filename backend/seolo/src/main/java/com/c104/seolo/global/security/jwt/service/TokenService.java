package com.c104.seolo.global.security.jwt.service;

import com.c104.seolo.global.security.jwt.dto.response.IssuedToken;
import org.springframework.security.core.Authentication;

public interface TokenService {

    IssuedToken issueToken(Authentication authentication);
    void removeAccessToken(String accessToken);
}
