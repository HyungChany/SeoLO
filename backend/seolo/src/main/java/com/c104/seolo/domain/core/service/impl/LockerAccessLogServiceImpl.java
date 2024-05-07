package com.c104.seolo.domain.core.service.impl;

import com.c104.seolo.domain.core.entity.Locker;
import com.c104.seolo.domain.core.entity.LockerAccessLog;
import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.core.repository.LockerAccessLogRepository;
import com.c104.seolo.domain.core.service.LockerAccessLogService;
import com.c104.seolo.domain.user.entity.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LockerAccessLogServiceImpl implements LockerAccessLogService {
    private final LockerAccessLogRepository lockerAccessLogRepository;

    @Autowired
    public LockerAccessLogServiceImpl(LockerAccessLogRepository lockerAccessLogRepository) {
        this.lockerAccessLogRepository = lockerAccessLogRepository;
    }

    @Override
    public void recordAccessLog(AppUser appUser, Locker locker, CODE statusCode) {
        LockerAccessLog lockerAccessLog = LockerAccessLog.builder()
                .appUser(appUser)
                .locker(locker)
                .statusCode(statusCode)
                .build();

        lockerAccessLogRepository.save(lockerAccessLog);
    }
}
