package com.c104.seolo.domain.core.service.impl;

import com.c104.seolo.domain.core.dto.LockerDto;
import com.c104.seolo.domain.core.dto.info.LockerInfo;
import com.c104.seolo.domain.core.dto.request.LockerEnrollRequest;
import com.c104.seolo.domain.core.dto.request.LockerRequest;
import com.c104.seolo.domain.core.dto.response.LockerResponse;
import com.c104.seolo.domain.core.entity.Locker;
import com.c104.seolo.domain.core.exception.LockerErrorCode;
import com.c104.seolo.domain.core.repository.LockerRepository;
import com.c104.seolo.domain.core.service.LockerService;
import com.c104.seolo.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LockerServiceImpl implements LockerService {
    private final LockerRepository lockerRepository;

    private List<LockerDto> getLockers(List<LockerInfo> lockerInfos) {
        return lockerInfos.stream()
                .map(info -> LockerDto.builder()
                        .id(info.getId())
                        .uid(info.getUid())
                        .isLocked(info.getLocked())
                        .battery(info.getBattery())
                        .build())
                .collect(Collectors.toList());
    };

    @Override
    public LockerResponse getCompanyLockers(String company_code) {
        List<LockerInfo> lockerInfos = lockerRepository.findAllByCompanyCode(company_code);
        List<LockerDto> lockerDtos = this.getLockers(lockerInfos);

        return LockerResponse.builder()
                .lockers(lockerDtos)
                .build();
    }

    @Override
    public void updateLocker(LockerRequest lockerRequest, String company_code, Long lock_id) {
        Locker locker = lockerRepository.findById(lock_id)
                .orElseThrow(() -> new CommonException(LockerErrorCode.NOT_EXIST_LOCKER));

        locker.setBattery(lockerRequest.getBattery());
        lockerRepository.save(locker);
    }

    @Override
    public LockerDto enrollLocker(LockerEnrollRequest lockerEnrollRequest) {
        Locker.builder()
                .company(lockerEnrollRequest.getCompanyCode()

                )
                .build();

        lockerRepository.save()

        return null;
    }

    @Override
    public LockerDto getLockerByUid(String lockerUid) {
        Locker locker = lockerRepository.findByUid(lockerUid).orElseThrow(
                () -> new CommonException(LockerErrorCode.NOT_EXIST_LOCKER));

        return locker.toResponse();
    }
}
