package com.c104.seolo.global.security.jwt.service.impl;

import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.security.jwt.JwtUtils;
import com.c104.seolo.global.security.jwt.dto.response.IssuedToken;
import com.c104.seolo.global.security.jwt.entity.InvalidJwtToken;
import com.c104.seolo.global.security.jwt.entity.JwtToken;
import com.c104.seolo.global.security.jwt.repository.InvalidTokenRepository;
import com.c104.seolo.global.security.jwt.repository.JwtTokenRepository;
import com.c104.seolo.global.security.jwt.service.JwtTokenService;
import com.c104.seolo.global.security.service.DBUserDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JwtTokenServiceImpl implements JwtTokenService {
    private final JwtUtils jwtUtils;
    private final JwtTokenRepository jwtTokenRepository;
    private final InvalidTokenRepository invalidTokenRepository;
    private final DBUserDetailService dbUserDetailService;

    @Override
    public IssuedToken issueToken(Authentication authentication, String deviceType) {
        // 인증객체 데이터를 통해 유저 정보를 찾아서
        // JWT 발급기에 보낸다
        // JwtUtils는 유저 레포지토리를 알 수 없도록 여기서 다 보내자
        AppUser appUser = dbUserDetailService.loadUserByUsername(authentication.getName());
        String accessToken = jwtUtils.issueAccessToken(appUser, deviceType);
        String refreshToken = jwtUtils.issueRefreshToken(appUser);

        // 기존 토큰 무효화 로직 추가
        JwtToken existingToken = jwtTokenRepository.findByUserIdAndDeviceType(appUser.getId(), deviceType);

        log.info("Issuing token for user: {}, deviceType: {}", appUser.getId(), deviceType);
        if (existingToken != null) {
            log.info("Existing token found: {}, deviceType: {}, invalidating and deleting", existingToken.getAccessToken(), existingToken.getDeviceType());
            invalidTokenRepository.save(new InvalidJwtToken(existingToken.getAccessToken()));
            jwtTokenRepository.delete(existingToken);
        } else {
            log.info("No existing token found for user: {}, deviceType: {}", appUser.getId(), deviceType);
        }

        // 새로운 토큰 저장
        jwtTokenRepository.save(
                JwtToken.builder()
                        .userId(appUser.getId())
                        .deviceType(deviceType)
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build()
        );

        return IssuedToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void removeAccessToken(String accessToken) {
        // 로그아웃시 유효한 access 토큰을
        // redis에 blacklist로 보내야함
        jwtUtils.validateAccessToken(accessToken);
        invalidTokenRepository.save(new InvalidJwtToken(accessToken));
    }
}
