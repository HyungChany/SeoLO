package com.c104.seolo.headquarter.employee.entity;

import com.c104.seolo.global.common.BaseEntity;
import com.c104.seolo.headquarter.company.entity.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@ToString
@Table(name = "employee")
public class Employee extends BaseEntity {

    @Id
    @Column(name = "employee_num", length = 15, nullable = false)
    private String employeeNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_code", referencedColumnName = "company_code", nullable = false)
    private Company company;

    @Column(name = "employee_name", length = 30, nullable = false)
    private String employeeName;

    @Column(name = "employee_title", length = 15, nullable = false)
    private String employeeTitle;

    @Column(name = "employee_team", length = 15, nullable = false)
    private String employeeTeam;

    @Column(name = "employee_birthday", nullable = false)
    private Date employeeBirthday;

    @Column(name = "employee_thum", length = 255)
    private String employeeThum;

    @Column(name = "employee_join_date", nullable = false)
    private Date employeeJoinDate;

    @Column(name = "employee_leave_date")
    private Date employeeLeaveDate;

}
