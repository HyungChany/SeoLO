package com.c104.seolo.domain.facility.service.impl;

import com.c104.seolo.domain.facility.dto.FacilityDto;
import com.c104.seolo.domain.facility.dto.info.FacilityInfo;
import com.c104.seolo.domain.facility.dto.request.FacilityRequest;
import com.c104.seolo.domain.facility.dto.response.FacilityListResponse;
import com.c104.seolo.domain.facility.entity.Facility;
import com.c104.seolo.domain.facility.exception.FacilityErrorCode;
import com.c104.seolo.domain.facility.repository.FacilityRepository;
import com.c104.seolo.domain.facility.service.FacilityService;
import com.c104.seolo.domain.machine.entity.Machine;
import com.c104.seolo.domain.machine.entity.MachineManager;
import com.c104.seolo.domain.machine.enums.Role;
import com.c104.seolo.domain.machine.exception.MachineErrorCode;
import com.c104.seolo.domain.machine.repository.MachineManagerRepository;
import com.c104.seolo.domain.machine.repository.MachineRepository;
import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.headquarter.company.repository.CompanyRepository;
import com.c104.seolo.headquarter.employee.dto.EmployeeDto;
import com.c104.seolo.headquarter.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacilityServiceImpl implements FacilityService {
    private final EmployeeService employeeService;
    private final FacilityRepository facilityRepository;
    private final CompanyRepository companyRepository;
    private final MachineRepository machineRepository;
    private final MachineManagerRepository machineManagerRepository;

    @Override
    public FacilityListResponse findFacilityByCompany(String companyCode) {
        if (!facilityRepository.existsByCompany(companyCode)) {
            throw new CommonException(FacilityErrorCode.NOT_EXIST_FACILITY);
        }

        List<FacilityInfo> facilityInfos = facilityRepository.getFacilities(companyCode);

        List<FacilityDto> facilityDtos = facilityInfos.stream()
                .map(facilityInfo -> FacilityDto.builder()
                        .id(facilityInfo.getId())
                        .name(facilityInfo.getFacilityName())
                        .build())
                .collect(Collectors.toList());

        return FacilityListResponse.builder()
                .facilities(facilityDtos)
                .build();
    }

    @Override
    public void createFacility(FacilityRequest facilityRequest, String companyCode) {
        boolean exists = facilityRepository.existsFacilitiesByName(facilityRequest.getName());
        if (exists) {
            throw new CommonException(FacilityErrorCode.FACILITY_ALREADY_EXISTS);
        }

        Facility facility = Facility.builder()
                .company(companyRepository.findByCompanyCodeEquals(companyCode))
                .facilityName(facilityRequest.getName())
                .facilityAddress(facilityRequest.getAddress())
                .facilityLayout(facilityRequest.getLayout())
                .facilityThum(facilityRequest.getThumbnail())
                .build();
        facilityRepository.save(facility);
    }

    @Override
    public void updateFacility(FacilityRequest facilityRequest, String companyCode, Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new CommonException(FacilityErrorCode.NOT_EXIST_FACILITY));

        boolean existsWithName = facilityRepository.existsByNameAndCompanyCodeNot(facilityRequest.getName(), companyCode, facilityId);
        if (existsWithName) {
            throw new CommonException(FacilityErrorCode.FACILITY_ALREADY_EXISTS);
        }

        facility.setFacilityName(facilityRequest.getName());
        facility.setFacilityAddress(facilityRequest.getAddress());
        facility.setFacilityLayout(facility.getFacilityLayout());
        facility.setFacilityThum(facility.getFacilityThum());
        facilityRepository.save(facility);
    }

    @Override
    public void deleteFacility(String companyCode, Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new CommonException(FacilityErrorCode.NOT_EXIST_FACILITY));

        if (!facility.getCompany().getCompanyCode().equals(companyCode)) {
            throw new CommonException(FacilityErrorCode.NOT_COMPANY_FACILITY);
        }

        List<Machine> machines = machineRepository.findByFacilityId(facilityId);
        for (Machine machine : machines) {
            Machine machineTemp = machineRepository.findById(machine.getId())
                    .orElseThrow(() -> new CommonException(MachineErrorCode.NOT_EXIST_MACHINE));

            if (!machineTemp.getFacility().getCompany().getCompanyCode().equals(companyCode)) {
                throw new CommonException(MachineErrorCode.NOT_COMPANY_MACHINE);
            }

            MachineManager mainManager = machineManagerRepository.findMachineManagerByMachineIdAndRole(machine.getId(), Role.Main);
            MachineManager subManager = machineManagerRepository.findMachineManagerByMachineIdAndRole(machine.getId(), Role.Sub);

            machineManagerRepository.delete(mainManager);
            machineManagerRepository.delete(subManager);
            machineRepository.delete(machineTemp);
        }

        facilityRepository.delete(facility);
    }

    @Override
    public FacilityListResponse findFacilitiesByEmployeeNum(String employeeNum) {
        // 사원에게 할당된 작업장 조회하기
        // 1. 사원번호로 사원 정보(회사코드)를 조회한다
        // 2. 회사 코드를 이용해서 작업장 DB를 모두 조회한다.

        EmployeeDto employee = employeeService.getEmployeeByEmployeeNum(employeeNum);
        return findFacilityByCompany(employee.getCompanyCode());
    }
}
