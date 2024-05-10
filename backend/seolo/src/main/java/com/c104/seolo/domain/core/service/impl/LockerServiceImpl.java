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
import com.c104.seolo.global.encryption.AesEncryption;
import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.headquarter.company.service.CompanyService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LockerServiceImpl implements LockerService {
    private final LockerRepository lockerRepository;
    private final CompanyService companyService;

    @Autowired
    public LockerServiceImpl(LockerRepository lockerRepository, CompanyService companyService) {
        this.lockerRepository = lockerRepository;
        this.companyService = companyService;
    }

    private List<LockerDto> getLockers(List<LockerInfo> lockerInfos) {
        return lockerInfos.stream()
                .map(info -> LockerDto.builder()
                        .id(info.getId())
                        .uid(info.getUid())
                        .isLocked(info.getLocked())
                        .battery(info.getBattery())
                        .build())
                .collect(Collectors.toList());
    }

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
    @Transactional
    public void enrollLocker(String companyCode, LockerEnrollRequest lockerEnrollRequest) {
        // 랜덤 대칭키를 생성한다.
        SecretKey binarySecretKey = AesEncryption.generateKey();

        // 대칭키는 base64로 인코딩 후 DB에 저장한다.
        Locker newLocker = Locker.builder()
                .company(companyService.findCompanyEntityByCompanyCode(companyCode))
                .uid(lockerEnrollRequest.getLockerUid())
                .encryptionKey(AesEncryption.getBase64EncodedKey(binarySecretKey))
                .build();


        lockerRepository.save(newLocker);
    }

    @Override
    public Locker getLockerByUid(String lockerUid) {
        return lockerRepository.findByUid(lockerUid).orElseThrow(
                () -> new CommonException(LockerErrorCode.NOT_EXIST_LOCKER));
    }

    @Override
    public void updateBatteryByLockerUid(String lockerUid, Integer battery) {
        Locker locker = lockerRepository.findByUid(lockerUid).orElseThrow(
                () -> new CommonException(LockerErrorCode.NOT_EXIST_LOCKER)
        );

        locker.changeBattery(battery);
        lockerRepository.save(locker);
    }
}
