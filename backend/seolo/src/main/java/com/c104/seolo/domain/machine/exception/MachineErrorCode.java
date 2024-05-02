package com.c104.seolo.domain.machine.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MachineErrorCode {
    NOT_EXIST_MACHINE("장비를 조회할 수 없습니다.", "ME01", HttpStatus.NOT_FOUND),
    MACHINE_ALREADY_EXISTS("같은 장비가 이미 존재합니다.", "ME03", HttpStatus.BAD_REQUEST),
    NOT_COMPANY_MACHINE("회사코드와 장비가 매칭되지 않습니다.", "ME01", HttpStatus.BAD_REQUEST),
    CANNOT_SEND_SAME_MANAGER("같은 사람이 매니저가 될 수 없습니다.", "ME04", HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;
}
