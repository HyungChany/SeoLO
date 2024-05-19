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

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
@Getter
public class EmployeeDto {
    @NotNull
    private String employeeNum;
    @NotNull
    private String employeeName;
    private String companyCode;
    @NotNull
    private String employeeTitle;
    @NotNull
    private String employeeTeam;
    @PastOrPresent
    private LocalDate employeeBirthday;
    private String employeeThum;
    @PastOrPresent
    private Date employeeJoinDate;
    private Date employeeLeaveDate;

    @Builder
    public EmployeeDto(String employeeNum, String employeeName, String companyCode, String employeeTitle, String employeeTeam, LocalDate employeeBirthday, String employeeThum, Date employeeJoinDate, Date employeeLeaveDate) {
        this.employeeNum = employeeNum;
        this.employeeName = employeeName;
        this.companyCode = companyCode;
        this.employeeTitle = employeeTitle;
        this.employeeTeam = employeeTeam;
        this.employeeBirthday = employeeBirthday;
        this.employeeThum = employeeThum;
        this.employeeJoinDate = employeeJoinDate;
        this.employeeLeaveDate = employeeLeaveDate;
    }
}
