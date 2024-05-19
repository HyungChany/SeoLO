package com.c104.seolo.domain.core.service.impl;

import com.c104.seolo.domain.core.entity.Locker;
import com.c104.seolo.domain.core.entity.LockerAccessLog;
import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.core.repository.LockerAccessLogRepository;
import com.c104.seolo.domain.core.service.LockerAccessLogService;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import com.c104.seolo.global.security.service.DBUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockerAccessLogServiceImpl implements LockerAccessLogService {
    private final LockerAccessLogRepository lockerAccessLogRepository;
    private final DBUserDetailService dbUserDetailService;


    @Override
    public void recordAccessLog(CCodePrincipal cCodePrincipal, Locker locker, CODE statusCode) {
        LockerAccessLog lockerAccessLog = LockerAccessLog.builder()
                .appUser(dbUserDetailService.loadUserById(cCodePrincipal.getId()))
                .locker(locker)
                .statusCode(statusCode)
                .build();

        lockerAccessLogRepository.save(lockerAccessLog);
    }
}
