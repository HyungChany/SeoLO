package com.c104.seolo.global.exception;

import com.c104.seolo.global.security.exception.AuthErrorCode;
import com.c104.seolo.global.security.exception.JwtErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class AuthException extends AuthenticationException {
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    public AuthException(AuthErrorCode authErrorCode) {
        super(authErrorCode.getMessage());
        this.httpStatus = authErrorCode.getHttpStatus();
        this.errorCode = authErrorCode.getErrorCode();
        this.message = authErrorCode.getMessage();
    }

    public AuthException(JwtErrorCode jwtErrorCode) {
        super(jwtErrorCode.getMessage());
        this.httpStatus = jwtErrorCode.getHttpStatus();
        this.errorCode = jwtErrorCode.getErrorCode();
        this.message = jwtErrorCode.getMessage();
    }
}
