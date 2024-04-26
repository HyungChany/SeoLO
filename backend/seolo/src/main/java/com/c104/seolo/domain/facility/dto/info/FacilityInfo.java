package com.c104.seolo.domain.facility.dto.info;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FacilityInfo {
    private Long id;
    private String facilityName;
    private String facilityAddress;
    private String facilityLayout;
    private String facilityThum;
}
