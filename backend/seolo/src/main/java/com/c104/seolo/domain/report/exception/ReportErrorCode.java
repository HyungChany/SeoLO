package com.c104.seolo.domain.report.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReportErrorCode {
    NOT_EXIST_REPORT("보고서를 찾을 수 없습니다.", "RT01", HttpStatus.NOT_FOUND),
    ;

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
