package com.c104.seolo.global.security.dto.response;

import com.c104.seolo.global.security.exception.AuthErrorCode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder
public class PINLoginFailureResponse extends PINLoginResponse{
    private final AuthErrorCode authErrorCode;

    public PINLoginFailureResponse(boolean isAuthenticated, Integer failCount, AuthErrorCode authErrorCode) {
        super(isAuthenticated, failCount);
        this.authErrorCode = authErrorCode;
    }
}
