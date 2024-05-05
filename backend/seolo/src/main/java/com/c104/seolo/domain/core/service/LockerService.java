package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.dto.LockerDto;
import com.c104.seolo.domain.core.dto.request.LockerEnrollRequest;
import com.c104.seolo.domain.core.dto.request.LockerRequest;
import com.c104.seolo.domain.core.dto.response.LockerResponse;
import com.c104.seolo.domain.core.entity.Locker;

public interface LockerService {
    public LockerResponse getCompanyLockers(String company_code);
    public void updateLocker(LockerRequest lockerRequest, String company_code, Long lock_id);

    public void enrollLocker(String company_code ,LockerEnrollRequest lockerEnrollRequest);
    public LockerDto getLockerByUid(String lockerUid);
}
