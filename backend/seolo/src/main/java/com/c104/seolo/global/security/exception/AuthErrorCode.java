package com.c104.seolo.global.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    NOT_EXIST_EMPLOYEE("AH01", "등록된 사원이 아닙니다.", HttpStatus.BAD_REQUEST),;

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}