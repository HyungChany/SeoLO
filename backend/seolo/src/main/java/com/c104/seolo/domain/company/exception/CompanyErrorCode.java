package com.c104.seolo.domain.company.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CompanyErrorCode {
    NOT_COMPANY_EMPLOYEE("해당 회사의 직원이 아닙니다.", HttpStatus.FORBIDDEN),
    NOT_EXIST_COMPANY_CODE("해당 회사가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
