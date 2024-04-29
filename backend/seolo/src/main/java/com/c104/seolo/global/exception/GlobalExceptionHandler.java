package com.c104.seolo.global.exception;

import com.c104.seolo.global.security.exception.SeoloErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> authExceptionHandler(AuthException e){
        return ResponseEntity.status(e.getHttpStatus())
                .body(SeoloErrorResponse.builder()
                .httpStatus(e.getHttpStatus())
                .errorCode(e.getErrorCode())
                .message(e.getMessage())
                .build());
    }
}
