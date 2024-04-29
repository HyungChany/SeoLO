package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.dto.request.LockerRequest;
import com.c104.seolo.domain.core.dto.response.LockerResponse;

public interface LockerService {
    public LockerResponse getCompanyLockers(String company_code);
    public void updateLocker(LockerRequest lockerRequest, String company_code, Long lock_id);
}
