package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.dto.TokenDto;
import com.c104.seolo.domain.user.entity.AppUser;

public interface CoreTokenService {
    TokenDto issueCoreAuthToken(AppUser appUser, String LockerUid);
    boolean isTokenExistedForUserId(Long userId);
    void deleteTokenByUserId(Long userId);
}
