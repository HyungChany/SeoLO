package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.dto.request.LockerEnrollRequest;
import com.c104.seolo.domain.core.dto.request.LockerRequest;
import com.c104.seolo.domain.core.dto.response.LockerResponse;
import com.c104.seolo.domain.core.entity.Locker;

public interface LockerService {
    LockerResponse getCompanyLockers(String company_code);
    void updateLocker(LockerRequest lockerRequest, String company_code, Long lock_id);
    void enrollLocker(String company_code ,LockerEnrollRequest lockerEnrollRequest);
    Locker getLockerByUid(String lockerUid);
    void updateBatteryByLockerUid(String lockerUid, Integer battery);
}
