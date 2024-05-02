package com.c104.seolo.domain.user.dto.response;

import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.user.enums.ROLES;
import com.c104.seolo.headquarter.employee.entity.Employee;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfoResponse {
    private Long id;
    private Employee employee;
    private ROLES ROLES;
    private CODE statusCODE;
    private String PIN;
    private boolean isLocked;

    @Builder
    public UserInfoResponse(Long id, Employee employee, com.c104.seolo.domain.user.enums.ROLES ROLES, CODE statusCODE, String PIN, boolean isLocked) {
        this.id = id;
        this.employee = employee;
        this.ROLES = ROLES;
        this.statusCODE = statusCODE;
        this.PIN = PIN;
        this.isLocked = isLocked;
    }
}
