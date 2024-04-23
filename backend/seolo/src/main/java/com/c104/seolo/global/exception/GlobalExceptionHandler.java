package com.c104.seolo.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ErrorResponse commonExceptionHandler(CommonException e){
        return ErrorResponse.builder(e, e.getHttpStatus(), e.getMessage()).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse runtimeExceptionHandler(RuntimeException e){
        return ErrorResponse.builder(e, HttpStatus.SERVICE_UNAVAILABLE, e.getMessage()).build();
    }

}
