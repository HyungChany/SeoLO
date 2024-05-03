package com.c104.seolo.global.security.jwt.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssuedToken {
    private String accessToken;
    private String refreshToken;

    @Builder
    public IssuedToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
