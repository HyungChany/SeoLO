package com.c104.seolo.domain.machine.service.impl;

import com.c104.seolo.domain.facility.entity.Facility;
import com.c104.seolo.domain.facility.exception.FacilityErrorCode;
import com.c104.seolo.domain.facility.repository.FacilityRepository;
import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.dto.MachineInfo;
import com.c104.seolo.domain.machine.dto.MachineListDto;
import com.c104.seolo.domain.machine.dto.MachineSpaceDto;
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
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.domain.user.exception.UserErrorCode;
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
    public MachineInfo findMachineByMachineId(String companyCode, Long machineId) {
        Optional<com.c104.seolo.domain.machine.dto.info.MachineInfo> machineOptional = machineRepository.findInfoById(machineId);
        com.c104.seolo.domain.machine.dto.info.MachineInfo machine = machineOptional.orElseThrow(() -> new CommonException(MachineErrorCode.NOT_EXIST_MACHINE));

        if (!machine.getCompanyCode().equals(companyCode)) {
            throw new CommonException(MachineErrorCode.NOT_COMPANY_MACHINE);
        }

        Optional<MachineManagerInfo> mainManagerOptional = machineManagerRepository.findMachineManagerInfoByMachineIdAndRole(machineId, Role.Main);
        Optional<MachineManagerInfo> subnManagerOptional = machineManagerRepository.findMachineManagerInfoByMachineIdAndRole(machineId, Role.Sub);

        Long mainManagerId = mainManagerOptional.map(MachineManagerInfo::getMachineManagerId).orElse(null);
        String mainManagerName = mainManagerOptional.map(MachineManagerInfo::getName).orElse(null);
        Long subManagerId = subnManagerOptional.map(MachineManagerInfo::getMachineManagerId).orElse(null);
        String subManagerName = subnManagerOptional.map(MachineManagerInfo::getName).orElse(null);

        return MachineInfo.builder()
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
                                    .machineId(info.getId())
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

    private void createMachineManager(String employeeNum, Machine machine, Role role) {
        if (employeeNum != null) {
            userRepository.findAppUserByEmployeeNum(employeeNum).ifPresent(user -> {
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
        if (machineRequest.getMainManagerNum().equals(machineRequest.getSubManagerNum())) {
            throw new CommonException(MachineErrorCode.CANNOT_SEND_SAME_MANAGER);
        }

        Machine machine = Machine.builder()
                .facility(facility)
                .name(machineRequest.getMachineName())
                .number(machineRequest.getMachineCode())
                .thum(machineRequest.getMachineThum())
                .introductionDate(machineRequest.getIntroductionDate())
                .lockerType(LockerType.NO)
                .build();
        Machine savedMachine = machineRepository.save(machine);

        createMachineManager(machineRequest.getMainManagerNum(), savedMachine, Role.Main);
        createMachineManager(machineRequest.getSubManagerNum(), savedMachine, Role.Sub);
    }

    @Override
    public void updateMachine(MachineRequest machineRequest, String companyCode, Long machineId) {
        Machine machine = machineRepository.findById(machineId).orElseThrow(() -> new CommonException(MachineErrorCode.NOT_EXIST_MACHINE));
        if (!machine.getFacility().getCompany().getCompanyCode().equals(companyCode)) {
            throw new CommonException(MachineErrorCode.NOT_COMPANY_MACHINE);
        }
        if (machineRequest.getMainManagerNum().equals(machineRequest.getSubManagerNum())) {
            throw new CommonException(MachineErrorCode.CANNOT_SEND_SAME_MANAGER);
        }

        Optional<AppUser> mainManagerOptional = userRepository.findAppUserByEmployeeNum(machineRequest.getMainManagerNum());
        if (mainManagerOptional.isEmpty()) {
            throw new CommonException(MachineErrorCode.NOT_EXIST_MACHINE_MANAGER);
        }
        mainManagerOptional.ifPresent(mainManager -> {
            if (!mainManager.getEmployee().getCompany().getCompanyCode().equals(companyCode)) {
                throw new CommonException(UserErrorCode.NOT_COMPANY_EMPLOYEE);
            }
        });

        Optional<AppUser> subManagerOptional = userRepository.findAppUserByEmployeeNum(machineRequest.getSubManagerNum());
        if (subManagerOptional.isEmpty()) {
            throw new CommonException(MachineErrorCode.NOT_EXIST_MACHINE_MANAGER);
        }
        subManagerOptional.ifPresent(subManager -> {
            if (!subManager.getEmployee().getCompany().getCompanyCode().equals(companyCode)) {
                throw new CommonException(UserErrorCode.NOT_COMPANY_EMPLOYEE);
            }
        });

        Optional<Facility> facilityOptional = facilityRepository.findById(machineRequest.getFacilityId());
        Facility facility = facilityOptional.orElseThrow(() -> new CommonException(FacilityErrorCode.NOT_EXIST_FACILITY));
        machine.setFacility(facility);

        machine.setName(machineRequest.getMachineName());
        machine.setNumber(machineRequest.getMachineCode());
        machine.setThum(machineRequest.getMachineThum());
        machine.setIntroductionDate(machineRequest.getIntroductionDate());

        machineRepository.save(machine);

        MachineManager mainManager = machineManagerRepository.findMachineManagerByMachineIdAndRole(machineId, Role.Main);
        mainManager.setUser(mainManagerOptional.get());
        machineManagerRepository.save(mainManager);

        MachineManager subManager = machineManagerRepository.findMachineManagerByMachineIdAndRole(machineId, Role.Sub);
        subManager.setUser(subManagerOptional.get());
        machineManagerRepository.save(subManager);
    }

    @Override
    public void updateMachineSpace(List<MachineSpaceDto> machineSpaceRequest, String companyCode) {
        if (machineSpaceRequest.isEmpty()) {
            throw new CommonException(MachineErrorCode.EMPTY_LIST);
        }
        machineSpaceRequest.forEach(spaceDto -> {
            Long machineId = spaceDto.getId();
            Float latitude = spaceDto.getLatitude();
            Float longitude = spaceDto.getLongitude();

            Machine machine = machineRepository.findById(machineId).orElseThrow(() -> new CommonException(MachineErrorCode.NOT_EXIST_MACHINE));

            if (!machine.getFacility().getCompany().getCompanyCode().equals(companyCode)) {
                throw new CommonException(MachineErrorCode.NOT_COMPANY_MACHINE);
            }
            machineRepository.save(machine);
        });
    }

    @Override
    public void deleteMachine(Long machineId, String companyCode) {
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new CommonException(MachineErrorCode.NOT_EXIST_MACHINE));

        if (!machine.getFacility().getCompany().getCompanyCode().equals(companyCode)) {
            throw new CommonException(MachineErrorCode.NOT_COMPANY_MACHINE);
        }

        MachineManager mainManager = machineManagerRepository.findMachineManagerByMachineIdAndRole(machineId, Role.Main);
        MachineManager subManager = machineManagerRepository.findMachineManagerByMachineIdAndRole(machineId, Role.Sub);

        machineManagerRepository.delete(mainManager);
        machineManagerRepository.delete(subManager);
        machineRepository.delete(machine);
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

    @Override
    public MachineDto getMachineByMachineId(Long machineId) {
        return MachineDto.of(machineRepository.findById(machineId).orElseThrow(() -> new CommonException(MachineErrorCode.NOT_EXIST_MACHINE)));
    }
}
