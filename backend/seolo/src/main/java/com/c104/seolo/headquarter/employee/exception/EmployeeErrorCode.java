package com.c104.seolo.headquarter.employee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EmployeeErrorCode {
    NOT_EXIST_EMPLOYEE("존재하지 않는 사원입니다.", "EE01", HttpStatus.NOT_FOUND),
    ;

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
