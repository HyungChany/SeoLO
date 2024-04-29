package com.c104.seolo.domain.checklist.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CheckListErrorCode {
    NOT_EXIST_CHECK_LIST("체크리스트를 조회할 수 없습니다.", "CT01", HttpStatus.BAD_REQUEST),
    NOT_COMPANY_CHECK_LIST("회사코드와 체크리스트가 매칭되지 않습니다.", "CT02", HttpStatus.BAD_REQUEST),
    CHECK_LIST_ALREADY_EXISTS("같은 내용의 체크리스트가 존재합니다.", "CT03", HttpStatus.BAD_REQUEST);

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
