package com.c104.seolo.domain.machine.service.impl;

import com.c104.seolo.domain.machine.dto.MachineListDto;
import com.c104.seolo.domain.machine.dto.info.MachineListInfo;
import com.c104.seolo.domain.machine.dto.response.MachineListResponse;
import com.c104.seolo.domain.machine.repository.MachineRepository;
import com.c104.seolo.domain.machine.service.MachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MachineServiceImpl implements MachineService {
    private final MachineRepository machineRepository;

    private List<MachineListDto> getMachineListDto(Optional<List<MachineListInfo>> machineListOptional) {
        return machineListOptional.map(machineList ->
                machineList.stream()
                        .map(info -> MachineListDto.builder()
                                .id(info.getId())
                                .facilityId(info.getFacilityId())
                                .facilityName(info.getFacilityName())
                                .machineName(info.getMachineName())
                                .machineCode(info.getMachineCode())
                                .introductionDate(info.getIntroductionDate())
                                .ManagerId(info.getManagerId())
                                .ManagerName(info.getManagerName())
                                .build())
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public MachineListResponse findMachineByCompanyAndFacility(String companyCode, Long facilityId) {
        Optional<List<MachineListInfo>> machineListOptional = machineRepository.getMachinesByFacilityIdAndCompany(facilityId, companyCode);

        List<MachineListDto> machineDtoList = getMachineListDto(machineListOptional);

        return MachineListResponse.builder()
               .machines(Optional.of(machineDtoList))
               .build();
    }
}

