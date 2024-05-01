package com.c104.seolo.domain.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LockerErrorCode {
    NOT_EXIST_LOCKER("자물쇠를 조회할 수 없습니다.", "LK01", HttpStatus.BAD_REQUEST);

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
