package com.c104.seolo.headquarter.employee.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
@Getter
public class EmployeeDto {
    @NotNull
    private String EmployeeNum;
    @NotNull
    private String EmployeeName;
    @NotNull
    private String EmployeeTitle;
    @NotNull
    private String EmployeeTeam;
    @PastOrPresent
    private LocalDate EmployeeBirthday;
    private String EmployeeThum;
    @PastOrPresent
    private Date employeeJoinDate;
    private Date employeeLeaveDate;
}
