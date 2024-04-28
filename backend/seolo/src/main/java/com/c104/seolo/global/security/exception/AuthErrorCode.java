package com.c104.seolo.global.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST, "USER-001", "등록된 회원이 아닙니다."),;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
