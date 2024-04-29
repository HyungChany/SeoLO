package com.c104.seolo.domain.facility.service;

import com.c104.seolo.domain.facility.dto.request.FacilityRequest;
import com.c104.seolo.domain.facility.dto.response.FacilityResponse;
import com.c104.seolo.domain.facility.entity.Facility;

public interface FacilityService {
    FacilityResponse findFacilityByCompany(String company_code);
    void createFacility(FacilityRequest facilityRequest, String company_code);
}
