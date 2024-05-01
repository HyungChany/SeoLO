package com.c104.seolo.domain.machine.service.impl;

import com.c104.seolo.domain.machine.dto.info.MachineListInfo;
import com.c104.seolo.domain.machine.dto.response.MachineListResponse;
import com.c104.seolo.domain.machine.repository.MachineRepository;
import com.c104.seolo.domain.machine.service.MachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MachineServiceImpl implements MachineService {
    private final MachineRepository machineRepository;

    @Override
    MachineListResponse getMachineListByFacility(String companyCode, Long facilityId){
        Optional<List<MachineListInfo>> machineListInfos = machineRepository.getMachinesByFacilityIdAndCompany(facilityId, companyCode);

        

        return MachineListResponse.builder()
                .build();
    }

}
