package com.c104.seolo.global.security.dto.response;

import com.c104.seolo.global.security.jwt.dto.response.IssuedToken;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtLoginSuccessResponse {
    private Long userId;
    private String username;
    private String companyCode;
    private final IssuedToken issuedToken;

    @Builder
    public JwtLoginSuccessResponse(IssuedToken issuedToken, String companyCode, String username, Long userId) {
        this.issuedToken = issuedToken;
        this.companyCode = companyCode;
        this.username = username;
        this.userId = userId;
    }
}
