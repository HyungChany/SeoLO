package com.c104.seolo.headquarter.company.entity;

import com.c104.seolo.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Entity
@Table(name = "company")
public class Company extends BaseEntity {
    // 외부 DB에서 불러온다는 가정하에 생성자 생성안했음
    @Id
    @Column(name = "company_code", length = 10 , nullable = false)
    private String companyCode;

    @Column(name = "company_name", nullable = false ,length = 60)
    private String companyName;

    @Column(name = "company_logo", nullable = false, length = 255)
    private String companyLogo;

    @Column(name = "company_registration_num", nullable = false, length = 255)
    private String companyRegistrationNum;

    @Column(name = "company_accident_manage_num", nullable = false, length = 255)
    private String companyAccidentManageNum;

    // JPA 프록시 객체 생성을 위한 기본생성자
    protected Company() {}
}
