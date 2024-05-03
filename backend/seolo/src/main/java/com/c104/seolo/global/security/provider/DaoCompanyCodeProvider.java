package com.c104.seolo.global.security.provider;

import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.exception.AuthException;
import com.c104.seolo.global.security.entity.DaoCompanycodeToken;
import com.c104.seolo.global.security.exception.AuthErrorCode;
import com.c104.seolo.global.security.service.DBUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DaoCompanyCodeProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final DBUserDetailService dbUserDetailService;

    @Autowired
    public DaoCompanyCodeProvider(PasswordEncoder passwordEncoder, DBUserDetailService dbUserDetailService) {
        this.passwordEncoder = passwordEncoder;
        this.dbUserDetailService = dbUserDetailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("인증 전 DaoToken provider 진입 : {}", authentication.getPrincipal().toString());
        if (authentication instanceof DaoCompanycodeToken) {
            DaoCompanycodeToken token = (DaoCompanycodeToken) authentication;

            String inputUsername = token.getName();
            String inputPassword = token.getCredentials().toString();
            String inputCompanyCode = token.getCompanyCode();
            log.debug("로그인 입력값 : {}, {}, {}", inputUsername,inputPassword,inputCompanyCode);

            AppUser appUser = dbUserDetailService.loadUserByUsername(inputUsername);
            if (!appUser.isMatchingCompanyCode(inputCompanyCode)) {
                throw new AuthException(AuthErrorCode.NOT_EQUAL_COMPNAY_CODE);
            }
            if (appUser.isLocked()) {
                throw new AuthException(AuthErrorCode.TOO_MANY_TRY_WRONG_LOGIN);
            }

            if (passwordEncoder.matches(inputPassword, appUser.getPassword())) {
                DaoCompanycodeToken authenticatedDaoCCToken = new DaoCompanycodeToken(
                        appUser,
                        null,
                        appUser.getAuthorities(),
                        inputCompanyCode);
                log.info("인증완료된 DaoToken : {}",authenticatedDaoCCToken);

                return authenticatedDaoCCToken;

            } else {
                throw new AuthException(AuthErrorCode.INVALID_PASSWORD);
            }
        }

        throw new AuthenticationException("인증관련 오류 [형식 미지정]") {};
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return DaoCompanycodeToken.class.isAssignableFrom(authentication);
    }
}
