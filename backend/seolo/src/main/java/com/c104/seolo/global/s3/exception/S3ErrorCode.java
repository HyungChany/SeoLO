package com.c104.seolo.global.s3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum S3ErrorCode {
    INVALID_EXTENSION("[확장자오류] : jpg, jpeg, svg, png 파일만 등록 가능합니다", "SF01" ,HttpStatus.BAD_REQUEST),
    EMPTY_UPLOAD_FILE("[빈파일오류] : 업로드 파일이 없습니다", "SF02", HttpStatus.BAD_REQUEST);

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
