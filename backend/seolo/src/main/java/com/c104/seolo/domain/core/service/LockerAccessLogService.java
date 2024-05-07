package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.entity.Locker;
import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.user.entity.AppUser;

public interface LockerAccessLogService {
    void recordAccessLog(AppUser appUser, Locker locker, CODE statusCode);
}
