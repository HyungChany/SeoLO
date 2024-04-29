package com.c104.seolo.domain.facility.service;

import com.c104.seolo.domain.facility.dto.response.FacilityResponse;

public interface FacilityService {
    FacilityResponse findFacilityByCompany(String company_code);
}
