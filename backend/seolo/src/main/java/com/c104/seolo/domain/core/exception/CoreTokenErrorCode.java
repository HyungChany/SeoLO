package com.c104.seolo.domain.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum  CoreTokenErrorCode {
    EXISTED_TOKEN("작업자에게 이미 발급된 토큰이 있습니다.", "CT01", HttpStatus.BAD_REQUEST),
    DUPLICATE_TOKEN_VALUE("이미 발급된 적 있는 식별자입니다.","CT02", HttpStatus.BAD_REQUEST),
    NOT_EXIST_TOKEN("해당 토큰을 찾을 수 없습니다.","CT03", HttpStatus.NOT_FOUND),
    NOT_SAME_WITH_USER("토큰을 발급받은 유저가 아닙니다.","CT04", HttpStatus.BAD_REQUEST),
    NOT_SAME_WITH_LOCKER("토큰을 발급받은 자물쇠가 아닙니다.","CT05", HttpStatus.BAD_REQUEST);

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
