package com.c104.seolo.domain.facility.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FacilityException {
    NOT_COMPANY_FACILITY("회사코드와 작업장이 매칭되지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_FACILITY("작업장을 조회할 수 없습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
