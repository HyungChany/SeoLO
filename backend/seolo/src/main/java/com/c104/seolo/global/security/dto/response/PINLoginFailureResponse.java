package com.c104.seolo.global.security.dto.response;

import com.c104.seolo.global.security.exception.AuthErrorCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PINLoginFailureResponse extends PINLoginResponse{
    private final AuthErrorCode authErrorCode;

    @Builder
    public PINLoginFailureResponse(boolean isAuthenticated, Integer failCount, AuthErrorCode authErrorCode) {
        super(isAuthenticated, failCount);
        this.authErrorCode = authErrorCode;
    }
}
