package com.c104.seolo.domain.task.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TaskErrorCode {
    NOT_EXIST_TASK("작업이 존재하지 않습니다.", "TY01", HttpStatus.NOT_FOUND),
    NOT_COMPANY_TASK("회사와 작업이 매칭되지 않습니다.", "TY02", HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
