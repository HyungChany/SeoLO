package com.c104.seolo.global.exception;

import com.c104.seolo.domain.checklist.exception.CheckListErrorCode;
import com.c104.seolo.headquarter.company.exception.CompanyErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends RuntimeException {

    private final String message;
//    private final String errorCode;
    private final HttpStatus httpStatus;

    public CommonException(CheckListErrorCode errorCode) {
        this.message = errorCode.getMessage();
//        this.errorCode = errorCode.getErrorCode();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public CommonException(CompanyErrorCode errorCode) {
        this.message = errorCode.getMessage();
//        this.errorCode = errorCode.getErrorCode();
        this.httpStatus = errorCode.getHttpStatus();
    }
}
