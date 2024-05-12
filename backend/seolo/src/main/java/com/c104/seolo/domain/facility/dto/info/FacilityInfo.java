package com.c104.seolo.domain.facility.dto.info;

import lombok.Builder;
import lombok.Getter;


@Getter
public class FacilityInfo {
    private Long id;
    private String facilityName;
    private String facilityAddress;
    private String facilityLayout;
    private String facilityThum;

    @Builder
    public FacilityInfo(Long id, String facilityName, String facilityAddress, String facilityLayout, String facilityThum) {
        this.id = id;
        this.facilityName = facilityName;
        this.facilityAddress = facilityAddress;
        this.facilityLayout = facilityLayout;
        this.facilityThum = facilityThum;
    }




}
