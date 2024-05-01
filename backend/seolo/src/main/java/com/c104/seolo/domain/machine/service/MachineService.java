package com.c104.seolo.domain.machine.service;

import com.c104.seolo.domain.machine.dto.response.MachineListResponse;

public interface MachineService {
    MachineListResponse findMachineByCompanyAndFacility(String companyCode, Long facilityId);
}
