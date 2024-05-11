package com.c104.seolo.domain.facility.service;

import com.c104.seolo.domain.facility.dto.request.FacilityRequest;
import com.c104.seolo.domain.facility.dto.response.FacilityListResponse;

public interface FacilityService {
    FacilityListResponse findFacilityByCompany(String companyCode);
    void createFacility(FacilityRequest facilityRequest, String companyCode);
    void updateFacility(FacilityRequest facilityRequest, String companyCode, Long facilityId);
    void deleteFacility(String companyCode, Long facilityId);
    FacilityListResponse findFacilitiesByEmployeeNum(String employeeNum);
}
