package com.c104.seolo.headquarter.employee.entity;

import com.c104.seolo.global.common.BaseEntity;
import com.c104.seolo.headquarter.company.entity.Company;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@ToString
@Getter
@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {
    // 외부 DB에서 불러온다는 가정하에 생성자 생성안했음
    @Id
    @Column(name = "employee_num", length = 15, nullable = false)
    private String employeeNum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_code", referencedColumnName = "company_code", nullable = false)
    private Company company;

    @Column(name = "employee_name", length = 30, nullable = false)
    private String employeeName;

    @Column(name = "employee_title", length = 15, nullable = false)
    private String employeeTitle;

    @Column(name = "employee_team", length = 15, nullable = false)
    private String employeeTeam;

    @Column(name = "employee_birthday", nullable = false)
    private LocalDate employeeBirthday;
    
    // 나중에 기본썸네일 넣어주기
    @Column(name = "employee_thum", length = 255, nullable = false)
    private String employeeThum;

    @Column(name = "employee_join_date", nullable = false)
    private Date employeeJoinDate;

    @Column(name = "employee_leave_date")
    private Date employeeLeaveDate;

    public String getBirthdayMonthDay() {
        if (employeeBirthday == null) {
            throw new IllegalStateException("Employee birthday is not set");
        }
        return employeeBirthday.format(DateTimeFormatter.ofPattern("MMdd"));
    }

    public String getCompanycode() {
        return this.company.getCompanyCode();
    }

    public boolean isMatchingCompanyCode(String companyCode) {
        return this.getCompany().isMatchingCompanyCode(companyCode);
    }

    // JPA 프록시 객체 생성을 위한 기본생성자
    protected Employee() {}
}
