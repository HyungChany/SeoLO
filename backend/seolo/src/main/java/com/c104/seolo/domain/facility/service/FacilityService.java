package com.c104.seolo.domain.facility.service;

import com.c104.seolo.domain.facility.dto.request.FacilityRequest;
import com.c104.seolo.domain.facility.dto.response.FacilityResponse;

public interface FacilityService {
    FacilityResponse findFacilityByCompany(String company_code);
    void createFacility(FacilityRequest facilityRequest, String company_code);
    void updateFacility(FacilityRequest facilityRequest, String company_code, Long facility_id);
    void deleteFacility(String company_code, Long facility_id);
}
