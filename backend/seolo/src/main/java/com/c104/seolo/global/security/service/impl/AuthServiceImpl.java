package com.c104.seolo.global.security.service.impl;

import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.domain.user.repository.UserRepository;
import com.c104.seolo.global.exception.AuthException;
import com.c104.seolo.global.security.dto.request.PINLoginRequest;
import com.c104.seolo.global.security.dto.response.PINLoginResponse;
import com.c104.seolo.global.security.dto.response.PINLoginFailureResponse;
import com.c104.seolo.global.security.exception.AuthErrorCode;
import com.c104.seolo.global.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public PINLoginResponse pinLogin(AppUser appUser, PINLoginRequest pinLoginRequest) {
        AppUser user = userRepository.findById(appUser.getId())
                .orElseThrow(() -> new AuthException(AuthErrorCode.NOT_EXIST_APPUSER));

        if (user.isLocked()) {
            throw new AuthException(AuthErrorCode.TOO_MANY_TRY_WRONG_LOGIN);
        }

        // 1. PIN 번호 일치 검증
        if (!passwordEncoder.matches(pinLoginRequest.getPIN(), user.getPassword())) {
            // 실패 카운트 증가
            Integer thisUserFailCount = user.upFailCount();
            userRepository.save(user);

            // 계정이 잠기면 로그아웃 필터 실행시키고
            // 메인화면으로 라우터로 이동
            //
            if (user.isLocked()) {
                throw new AuthException(AuthErrorCode.TOO_MANY_TRY_WRONG_LOGIN);
            }

            return PINLoginFailureResponse.builder()
                    .isAuthenticated(false)
                    .failCount(thisUserFailCount)
                    .authErrorCode(AuthErrorCode.INVALID_PIN)
                    .build();
        }

        // 2. 일치 시
        user.clearFailCount();
        userRepository.save(user);
        return PINLoginResponse.builder()
                .isAuthenticated(true)
                .build();
    }
}
