package com.c104.seolo.domain.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CoreErrorCode {
    STATE_REFLECTION_ERROR("상태 설정 중 에러가 발생했습니다." ,"CE01", HttpStatus.BAD_REQUEST),
    IS_RELLY_SAME_LOCKER("해당 유저,장비가 잠금을 하려한 자물쇠가 맞는지 확인해주세요." ,"CE02", HttpStatus.BAD_REQUEST);
    ;

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
