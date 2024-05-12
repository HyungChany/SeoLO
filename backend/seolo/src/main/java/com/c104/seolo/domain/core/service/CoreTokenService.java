package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.dto.TokenDto;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;

public interface CoreTokenService {
    TokenDto issueCoreAuthToken(CCodePrincipal cCodePrincipal, String LockerUid);
    boolean isTokenExistedForUserId(Long userId);
    void deleteTokenByUserId(Long userId);
    void deleteTokenByTokenValue(String tokenValue);
    boolean validateTokenWithUser(String tokenValue, Long userId);
    boolean validateTokenWithLocker(String tokenValue, String lockerId);
}
