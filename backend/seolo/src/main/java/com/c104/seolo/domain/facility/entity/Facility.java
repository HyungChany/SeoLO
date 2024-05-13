package com.c104.seolo.domain.facility.entity;

import com.c104.seolo.domain.facility.dto.FacilityDto;
import com.c104.seolo.global.common.BaseEntity;
import com.c104.seolo.headquarter.company.entity.Company;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@Entity
@Table(name = "facility")
public class Facility extends BaseEntity {
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "facility_id", nullable = false)
    @Id
    private Long id;

    @JoinColumn(name = "facility_company_code", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Company company;

    @Column(name = "facility_name" ,length = 20, nullable = false)
    private String facilityName;

    @Column(name = "facility_address" ,length = 255, nullable = false)
    private String facilityAddress;

    @Column(name = "facility_layout" ,length = 255, nullable = false)
    private String facilityLayout;

    @Column(name = "facility_thum" ,length = 255)
    private String facilityThum;

    @Builder
    private Facility(Long id, Company company, String facilityName, String facilityAddress, String facilityLayout, String facilityThum) {
        this.id = id;
        this.company = company;
        this.facilityName = facilityName;
        this.facilityAddress = facilityAddress;
        this.facilityLayout = facilityLayout;
        this.facilityThum = facilityThum;
    }

    public Facility() {}

    public void changeLayout(String newLayout) {
        this.facilityLayout = newLayout;
    }
}
