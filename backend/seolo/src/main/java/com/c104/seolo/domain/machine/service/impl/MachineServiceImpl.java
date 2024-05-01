package com.c104.seolo.domain.machine.service.impl;

import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.dto.MachineListDto;
import com.c104.seolo.domain.machine.dto.info.MachineInfo;
import com.c104.seolo.domain.machine.dto.info.MachineListInfo;
import com.c104.seolo.domain.machine.dto.response.MachineListResponse;
import com.c104.seolo.domain.machine.exception.MachineErrorCode;
import com.c104.seolo.domain.machine.repository.MachineRepository;
import com.c104.seolo.domain.machine.service.MachineService;
import com.c104.seolo.global.exception.CommonException;
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

    @Override
    public MachineDto findMachineByMachineId(String companyCode, Long machineId) {
        Optional<MachineInfo> machineOptional = machineRepository.findById(machineId);
        MachineInfo machine = machineOptional.orElseThrow(() -> new CommonException(MachineErrorCode.NOT_EXIST_MACHINE));


        if (!machine.getCompanyCode().equals(companyCode)) {
            throw new CommonException(MachineErrorCode.NOT_COMPANY_MACHINE);
        }

        return MachineDto.builder()
                .id(machine.getId())
                .facilityId(machine.getFacilityId())
                .facilityName(machine.getFacilityName())
                .machineName(machine.getMachineName())
                .machineCode(machine.getMachineCode())
                .machineThumbnail(machine.getMachineThumbnail())
                .introductionDate(machine.getIntroductionDate())
                .mainManagerId(machine.getMainManagerId())
                .mainManagerName(machine.getMainManagerName())
                .subManagerId(machine.getSubManagerId())
                .subManagerName(machine.getSubManagerName())
                .build();
    }

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
                                .mainManagerId(info.getMainManagerId())
                                .mainManagerName(info.getMainManagerName())
                                .subManagerId(info.getSubManagerId())
                                .subManagerName(info.getSubManagerName())
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

