package com.c104.seolo.domain.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LockerErrorCode {
    NOT_EXIST_LOCKER("존재하지 않는 자물쇠입니다.", "LK01", HttpStatus.NOT_FOUND);

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
