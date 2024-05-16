package com.c104.seolo.domain.marker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MarkerErrorCode {
    NOT_EXIST_MARKER("MR01", "해당 마커를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ALREADY_ENROLLED_MACHINE("MR02", "이미 마커로 등록된 장비입니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
