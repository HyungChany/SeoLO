package com.c104.seolo.global.statistic.service.impl;

import com.c104.seolo.domain.core.repository.LockerRepository;
import com.c104.seolo.domain.machine.repository.MachineRepository;
import com.c104.seolo.global.statistic.service.StaticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StaticServiceImpl implements StaticService {

    private final MachineRepository machineRepository;
    private final LockerRepository lockerRepository;

    @Override
    public Long countAllMachinesByFacility(Long facilityId) {
        return machineRepository.countByFacilityId(facilityId);
    }

    @Override
    public Long countAllLockerByCompanyCode(String companyCode) {
        return lockerRepository.countByCompanyCode(companyCode);
    }
}
