package com.c104.seolo.domain.machine.service.impl;

import com.c104.seolo.domain.facility.entity.Facility;
import com.c104.seolo.domain.facility.exception.FacilityErrorCode;
import com.c104.seolo.domain.facility.repository.FacilityRepository;
import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.dto.MachineListDto;
import com.c104.seolo.domain.machine.dto.info.MachineInfo;
import com.c104.seolo.domain.machine.dto.info.MachineListInfo;
import com.c104.seolo.domain.machine.dto.info.MachineManagerInfo;
import com.c104.seolo.domain.machine.dto.request.MachineRequest;
import com.c104.seolo.domain.machine.dto.response.MachineListResponse;
import com.c104.seolo.domain.machine.entity.Machine;
import com.c104.seolo.domain.machine.entity.MachineManager;
import com.c104.seolo.domain.machine.enums.LockerType;
import com.c104.seolo.domain.machine.enums.Role;
import com.c104.seolo.domain.machine.exception.MachineErrorCode;
import com.c104.seolo.domain.machine.repository.MachineManagerRepository;
import com.c104.seolo.domain.machine.repository.MachineRepository;
import com.c104.seolo.domain.machine.service.MachineService;
import com.c104.seolo.domain.user.repository.UserRepository;
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
    private final MachineManagerRepository machineManagerRepository;
    private final FacilityRepository facilityRepository;
    private final UserRepository userRepository;

    @Override
    public MachineDto findMachineByMachineId(String companyCode, Long machineId) {
        Optional<MachineInfo> machineOptional = machineRepository.findInfoById(machineId);
        MachineInfo machine = machineOptional.orElseThrow(() -> new CommonException(MachineErrorCode.NOT_EXIST_MACHINE));

        if (!machine.getCompanyCode().equals(companyCode)) {
            throw new CommonException(MachineErrorCode.NOT_COMPANY_MACHINE);
        }

        Optional<MachineManagerInfo> mainManagerOptional = machineManagerRepository.findMachineManagerInfoByMachineIdAndRole(machineId, Role.Main);
        Optional<MachineManagerInfo> subnManagerOptional = machineManagerRepository.findMachineManagerInfoByMachineIdAndRole(machineId, Role.Sub);

        Long mainManagerId = mainManagerOptional.map(MachineManagerInfo::getMachineManagerId).orElse(null);
        String mainManagerName = mainManagerOptional.map(MachineManagerInfo::getName).orElse(null);
        Long subManagerId = subnManagerOptional.map(MachineManagerInfo::getMachineManagerId).orElse(null);
        String subManagerName = subnManagerOptional.map(MachineManagerInfo::getName).orElse(null);

        return MachineDto.builder()
                .id(machine.getId())
                .facilityId(machine.getFacilityId())
                .facilityName(machine.getFacilityName())
                .machineName(machine.getMachineName())
                .machineCode(machine.getMachineCode())
                .machineThumbnail(machine.getMachineThumbnail())
                .introductionDate(machine.getIntroductionDate())
                .mainManagerId(mainManagerId)
                .mainManagerName(mainManagerName)
                .subManagerId(subManagerId)
                .subManagerName(subManagerName)
                .build();
    }

    private List<MachineListDto> getMachineListDto(Optional<List<MachineListInfo>> machineListOptional) {
        return machineListOptional.map(machineList -> machineList.stream()
                        .map(info -> {
                            Optional<MachineManagerInfo> mainManagerOptional = machineManagerRepository.findMachineManagerInfoByMachineIdAndRole(info.getId(), Role.Main);
                            Optional<MachineManagerInfo> subnManagerOptional = machineManagerRepository.findMachineManagerInfoByMachineIdAndRole(info.getId(), Role.Sub);

                            Long mainManagerId = mainManagerOptional.map(MachineManagerInfo::getMachineManagerId).orElse(null);
                            String mainManagerName = mainManagerOptional.map(MachineManagerInfo::getName).orElse(null);
                            Long subManagerId = subnManagerOptional.map(MachineManagerInfo::getMachineManagerId).orElse(null);
                            String subManagerName = subnManagerOptional.map(MachineManagerInfo::getName).orElse(null);

                            return MachineListDto.builder()
                                    .id(info.getId())
                                    .facilityId(info.getFacilityId())
                                    .facilityName(info.getFacilityName())
                                    .machineName(info.getMachineName())
                                    .machineCode(info.getMachineCode())
                                    .introductionDate(info.getIntroductionDate())
                                    .mainManagerId(mainManagerId)
                                    .mainManagerName(mainManagerName)
                                    .subManagerId(subManagerId)
                                    .subManagerName(subManagerName)
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }


    private void createMachineManager(Long userId, Machine machine, Role role) {
        if (userId != null) {
            userRepository.findById(userId).ifPresent(user -> {
                MachineManager mm = MachineManager.builder()
                        .machine(machine)
                        .user(user)
                        .mm_role(role)
                        .build();
                machineManagerRepository.save(mm);
            });
        }
    }

    @Override
    public void createMachine(MachineRequest machineRequest, String companyCode) {
        Optional<Facility> facilityOptional = facilityRepository.findById(machineRequest.getFacilityId());
        Facility facility = facilityOptional.orElseThrow(() -> new CommonException(FacilityErrorCode.NOT_EXIST_FACILITY));
        if (!facility.getCompany().getCompanyCode().equals(companyCode)) {
            throw new CommonException(FacilityErrorCode.NOT_COMPANY_FACILITY);
        }
        if (machineRequest.getMainManagerId().equals(machineRequest.getSubManagerId())) {
            throw new CommonException(MachineErrorCode.CANNOT_SEND_SAME_MANAGER);
        }

        Machine machine = Machine.builder()
                .facility(facility)
                .name(machineRequest.getMachineName())
                .number(machineRequest.getMachineCode())
                .thum(machineRequest.getMachineThum())
                .introductionDate(machineRequest.getIntroductionDate())
                .longitude(-1.0f)
                .latitude(-1.0f)
                .lockerType(LockerType.NO)
                .build();
        Machine savedMachine = machineRepository.save(machine);

        createMachineManager(machineRequest.getMainManagerId(), savedMachine, Role.Main);
        createMachineManager(machineRequest.getSubManagerId(), savedMachine, Role.Sub);
    }

    @Override
    public void updateMachineSpace(Long machineId ,Float latitude, Float longitude, String companyCode) {
        Optional<Machine> machineOptional = machineRepository.findById(machineId);
        Machine machine = machineOptional.orElseThrow(() -> new CommonException(MachineErrorCode.NOT_EXIST_MACHINE));
        if (!machine.getFacility().getCompany().getCompanyCode().equals(companyCode)) {
            throw new CommonException(MachineErrorCode.NOT_COMPANY_MACHINE);
        }

        machine.setLatitude(latitude);
        machine.setLongitude(longitude);
        machineRepository.save(machine);
    }

    @Override
    public MachineListResponse findMachineByCompanyAndFacility(String companyCode, Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new CommonException(FacilityErrorCode.NOT_EXIST_FACILITY));

        if (!facility.getCompany().getCompanyCode().equals(companyCode)) {
            throw new CommonException(FacilityErrorCode.NOT_COMPANY_FACILITY);
        }

        Optional<List<MachineListInfo>> machineListOptional = machineRepository.getMachinesByFacilityIdAndCompany(facilityId, companyCode);

        List<MachineListDto> machineDtoList = getMachineListDto(machineListOptional);

        return MachineListResponse.builder()
                .machines(Optional.of(machineDtoList))
                .build();
    }
}

