package com.c104.seolo.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode {
    APPUSER_ALREADY_IN_DB("UR01","이미 가입된 회원입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_APPUSER("UR02","아이디 또는 비밀번호가 다릅니다.",HttpStatus.BAD_REQUEST),
    NOT_MANAGER("UR03", "관리자만 접근 가능한 경로입니다.", HttpStatus.FORBIDDEN),
    ;

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
