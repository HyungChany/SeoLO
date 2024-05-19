package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.entity.Locker;
import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;

public interface LockerAccessLogService {
    void recordAccessLog(CCodePrincipal cCodePrincipal, Locker locker, CODE statusCode);
}
