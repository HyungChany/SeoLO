package com.c104.seolo.global.security.jwt.service.impl;

import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.security.jwt.JwtUtils;
import com.c104.seolo.global.security.jwt.dto.response.IssuedToken;
import com.c104.seolo.global.security.jwt.entity.InvalidToken;
import com.c104.seolo.global.security.jwt.entity.JwtToken;
import com.c104.seolo.global.security.jwt.repository.InvalidTokenRepository;
import com.c104.seolo.global.security.jwt.repository.TokenRepository;
import com.c104.seolo.global.security.jwt.service.TokenService;
import com.c104.seolo.global.security.service.DBUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtTokenService implements TokenService {
    private final JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;
    private final InvalidTokenRepository invalidTokenRepository;
    private final DBUserDetailService dbUserDetailService;

    @Autowired
    public JwtTokenService(JwtUtils jwtUtils, TokenRepository tokenRepository, InvalidTokenRepository invalidTokenRepository, DBUserDetailService dbUserDetailService) {
        this.jwtUtils = jwtUtils;
        this.tokenRepository = tokenRepository;
        this.invalidTokenRepository = invalidTokenRepository;
        this.dbUserDetailService = dbUserDetailService;
    }

    @Override
    public IssuedToken issueToken(Authentication authentication) {
        // 인증객체 데이터를 통해 유저 정보를 찾아서
        // JWT 발급기에 보낸다
        // JwtUtils는 유저 레포지토리를 알 수 없도록 여기서 다 보내자
        AppUser appUser = dbUserDetailService.loadUserByUsername(authentication.getName());
        String accessToken = jwtUtils.issueAccessToken(appUser);
        String refreshToken = jwtUtils.issueRefreshToken(appUser);

        tokenRepository.save(
                JwtToken.builder()
                        .id(appUser.getId())
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
        invalidTokenRepository.save(new InvalidToken(accessToken));
    }
}
