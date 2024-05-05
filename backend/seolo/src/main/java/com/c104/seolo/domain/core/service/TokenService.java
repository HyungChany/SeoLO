package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.entity.Token;

public interface TokenService {

    Token issueCoreAuthToken(Long userId, String LockerUid);



    boolean isTokenExistedForUserId(Long userId);

}
