package com.c104.seolo.global.security.provider;

import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.exception.AuthException;
import com.c104.seolo.global.security.entity.DaoCompanycodeToken;
import com.c104.seolo.global.security.exception.AuthErrorCode;
import com.c104.seolo.global.security.service.impl.DBUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

        if (authentication instanceof DaoCompanycodeToken) {
            DaoCompanycodeToken token = (DaoCompanycodeToken) authentication;

            String inputUsername = token.getName();
            String inputPassword = token.getCredentials().toString();
            String inputCompanyCode = token.getCompanyCode();

            log.debug("로그인 입력값 : {}, {}, {}", inputUsername,inputPassword,inputCompanyCode);

            AppUser appUser = null;

            try {
                appUser = dbUserDetailService.loadUserByUsername(inputUsername);

            } catch (UsernameNotFoundException e) {
                throw new AuthException(AuthErrorCode.NOT_EXIST_USER);
            }

            if (passwordEncoder.matches(inputPassword, appUser.getPassword())) {

                return new DaoCompanycodeToken(
                        appUser.getUsername(),
                        null,
                        appUser.getAuthorities(),
                        inputCompanyCode
                        );
            }

        }
        throw new AuthenticationException("Unsupported authentication token") {};
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return DaoCompanycodeToken.class.isAssignableFrom(authentication);
    }
}
