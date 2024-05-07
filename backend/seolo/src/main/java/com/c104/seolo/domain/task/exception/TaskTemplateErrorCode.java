package com.c104.seolo.domain.task.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TaskTemplateErrorCode {
    NOT_EXIST_TASK_TEMPLATE("템플릿이 존재하지 않습니다.", "TT01", HttpStatus.NOT_FOUND);

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
