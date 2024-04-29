package com.c104.seolo.domain.facility.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FacilityErrorCode {
    NOT_COMPANY_FACILITY("회사코드와 작업장이 매칭되지 않습니다.", "FY01", HttpStatus.BAD_REQUEST),
    NOT_EXIST_FACILITY("작업장을 조회할 수 없습니다.", "FY02", HttpStatus.BAD_REQUEST),
    FACILITY_ALREADY_EXISTS("같은 작업장이 이미 존재합니다.", "FY03", HttpStatus.BAD_REQUEST);

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
