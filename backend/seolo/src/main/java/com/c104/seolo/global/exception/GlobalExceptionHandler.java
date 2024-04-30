package com.c104.seolo.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<?> commonExceptionHandler(CommonException e){
        return ResponseEntity.status(e.getHttpStatus())
                .body(SeoloErrorResponse.builder()
                        .httpStatus(e.getHttpStatus())
                        .errorCode(e.getErrorCode())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(SeoloErrorResponse.builder()
                        .httpStatus(HttpStatus.SERVICE_UNAVAILABLE)
                        .errorCode("UNEXPECTED_ERROR")
                        .message(e.getMessage())
                        .build());
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


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SeoloErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        String errorCodes = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField())
                .collect(Collectors.joining(", "));

        SeoloErrorResponse response = SeoloErrorResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorCode("VALIDATION_ERROR: " + errorCodes)
                .message(errorMessage)
                .build();

        return ResponseEntity.badRequest().body(response);
    }


}
