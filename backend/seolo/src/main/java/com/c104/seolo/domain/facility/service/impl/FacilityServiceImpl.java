package com.c104.seolo.domain.facility.service.impl;

import com.c104.seolo.domain.facility.dto.FacilityDto;
import com.c104.seolo.domain.facility.dto.info.FacilityInfo;
import com.c104.seolo.domain.facility.dto.response.FacilityResponse;
import com.c104.seolo.domain.facility.exception.FacilityErrorCode;
import com.c104.seolo.domain.facility.repository.FacilityRepository;
import com.c104.seolo.domain.facility.service.FacilityService;
import com.c104.seolo.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacilityServiceImpl implements FacilityService {
    private final FacilityRepository facilityRepository;

    @Override
    public FacilityResponse findFacilityByCompany(String company_code) {
        if (facilityRepository.existsByCompany(company_code)) {
            throw new CommonException(FacilityErrorCode.NOT_EXIST_FACILITY);
        }

        List<FacilityInfo> facilityInfos = facilityRepository.getFacilities(company_code);

        List<FacilityDto> facilityDtos = facilityInfos.stream()
                .map(facilityInfo -> FacilityDto.builder()
                        .id(facilityInfo.getId())
                        .name(facilityInfo.getFacilityName())
                        .build())
                .collect(Collectors.toList());

        return FacilityResponse.builder()
                .facilities(facilityDtos)
                .build();
    }
}
