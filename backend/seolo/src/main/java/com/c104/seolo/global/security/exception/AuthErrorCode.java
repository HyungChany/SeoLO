package com.c104.seolo.global.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode {
    NOT_EXIST_EMPLOYEE("AH01", "등록된 사원이 아닙니다.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("AH02", "아이디 또는 비밀번호가 다릅니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_APPUSER("AH03","아이디 또는 비밀번호가 다릅니다.",HttpStatus.BAD_REQUEST),
    NOT_EQUAL_COMPNAY_CODE("AH04","회사코드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_SAME_AS_CHECKPWD("AH05", "비밀번호와 확인 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_PIN("AH06", "PIN번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    TOO_MANY_TRY_WRONG_LOGIN("AH07", "너무 많은 로그인 실패로 계정이 잠겼습니다. 관리자에게 문의하세요", HttpStatus.BAD_REQUEST),
    ;

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
