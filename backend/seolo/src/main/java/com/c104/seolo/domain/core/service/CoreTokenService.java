package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.entity.Token;
import com.c104.seolo.domain.user.entity.AppUser;

public interface CoreTokenService {

    Token issueCoreAuthToken(AppUser appUser, String LockerUid);
    boolean isTokenExistedForUserId(Long userId);

}
