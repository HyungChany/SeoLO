package com.c104.seolo.global.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {
    NOT_EXISTS_TOKEN("JT01","유효하지 않은 정보입니다.", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN("JT02","토큰이 만료되었습니다.",HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("JT03","사용할 수 없는 토큰 입니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_SIGNATURE_ERROR("JT04","잘못된 토큰", HttpStatus.UNAUTHORIZED),
    NOT_SUPPORT_TOKEN("JT05","지원되지 않는 토큰", HttpStatus.UNAUTHORIZED),
    NO_TOKEN("JT06","토큰이 없습니다.", HttpStatus.UNAUTHORIZED);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
