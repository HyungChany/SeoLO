package com.c104.seolo.global.exception;

import com.c104.seolo.domain.core.exception.CoreTokenErrorCode;
import com.c104.seolo.domain.machine.exception.MachineErrorCode;
import com.c104.seolo.domain.task.exception.TaskErrorCode;
import com.c104.seolo.global.security.exception.JwtErrorCode;
import com.c104.seolo.headquarter.company.exception.CompanyErrorCode;
import com.c104.seolo.domain.checklist.exception.CheckListErrorCode;
import com.c104.seolo.domain.facility.exception.FacilityErrorCode;
import com.c104.seolo.domain.core.exception.LockerErrorCode;
import com.c104.seolo.domain.user.exception.UserErrorCode;
import com.c104.seolo.headquarter.employee.exception.EmployeeErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends RuntimeException {
    private final String message;
    private final String errorCode;
    private final HttpStatus httpStatus;

    public CommonException(CheckListErrorCode e) {
        this.message = e.getMessage();
        this.errorCode = e.getErrorCode();
        this.httpStatus = e.getHttpStatus();
    }

    public CommonException(CompanyErrorCode e) {
        this.message = e.getMessage();
        this.errorCode = e.getErrorCode();
        this.httpStatus = e.getHttpStatus();
    }

    public CommonException(FacilityErrorCode e) {
        this.message = e.getMessage();
        this.errorCode = e.getErrorCode();
        this.httpStatus = e.getHttpStatus();
    }

    public CommonException(UserErrorCode e) {
        this.message = e.getMessage();
        this.errorCode = e.getErrorCode();
        this.httpStatus = e.getHttpStatus();
    }

    public CommonException(LockerErrorCode e) {
        this.message = e.getMessage();
        this.errorCode = e.getErrorCode();
        this.httpStatus = e.getHttpStatus();
    }

    public CommonException(MachineErrorCode e) {
        this.message = e.getMessage();
        this.errorCode = e.getErrorCode();
        this.httpStatus = e.getHttpStatus();
    }

    public CommonException(JwtErrorCode e) {
        this.message = e.getMessage();
        this.errorCode = e.getErrorCode();
        this.httpStatus = e.getHttpStatus();
    }

    public CommonException(EmployeeErrorCode e) {
        this.message = e.getMessage();
        this.errorCode = e.getErrorCode();
        this.httpStatus = e.getHttpStatus();
    }

    public CommonException(TaskErrorCode e) {
        this.message = e.getMessage();
        this.errorCode = e.getErrorCode();
        this.httpStatus = e.getHttpStatus();
    }

    public CommonException(CoreTokenErrorCode e) {
        this.message = e.getMessage();
        this.errorCode = e.getErrorCode();
        this.httpStatus = e.getHttpStatus();
    }
}
