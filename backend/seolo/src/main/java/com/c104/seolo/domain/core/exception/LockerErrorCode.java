package com.c104.seolo.domain.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LockerErrorCode {
    NOT_EXIST_LOCKER("존재하지 않는 자물쇠입니다.", "LK01", HttpStatus.NOT_FOUND),
    NOT_ALLOWED_CODE_WHEN_UPDATE_LOKCED("해당 상태(행동)코드로는 자물쇠 잠금 상태를 바꿀 수 없습니다.", "LK02", HttpStatus.BAD_REQUEST);

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
