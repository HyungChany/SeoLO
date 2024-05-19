package com.c104.seolo.headquarter.company.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CompanyErrorCode {
    NOT_COMPANY_EMPLOYEE("해당 회사의 직원이 아닙니다.", "CY01", HttpStatus.FORBIDDEN),
    NOT_EXIST_COMPANY_CODE("해당 회사가 존재하지 않습니다.", "CY02", HttpStatus.BAD_REQUEST),
    NO_COMPANY_CODE("회사 코드를 보내지 않았습니다.", "CY03", HttpStatus.BAD_REQUEST),;

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
