package com.c104.seolo.global.security.service.impl;

import com.c104.seolo.domain.user.dto.request.UserLoginRequest;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.domain.user.repository.UserRepository;
import com.c104.seolo.global.exception.AuthException;
import com.c104.seolo.global.security.dto.request.PINLoginRequest;
import com.c104.seolo.global.security.dto.request.PINResetRequest;
import com.c104.seolo.global.security.dto.response.JwtLoginSuccessResponse;
import com.c104.seolo.global.security.dto.response.PINLoginFailureResponse;
import com.c104.seolo.global.security.dto.response.PINLoginResponse;
import com.c104.seolo.global.security.entity.DaoCompanycodeToken;
import com.c104.seolo.global.security.exception.AuthErrorCode;
import com.c104.seolo.global.security.jwt.dto.response.IssuedToken;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import com.c104.seolo.global.security.jwt.service.JwtTokenService;
import com.c104.seolo.global.security.service.AuthService;
import com.c104.seolo.global.security.service.DBUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final DBUserDetailService dbUserDetailService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository, JwtTokenService jwtTokenService, DBUserDetailService dbUserDetailService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.dbUserDetailService = dbUserDetailService;
    }

    @Override
    public JwtLoginSuccessResponse userLogin(UserLoginRequest userLoginRequest) {

        Authentication authentication = authenticationManager.authenticate(new DaoCompanycodeToken(
                userLoginRequest.getUsername(),
                userLoginRequest.getPassword(),
                userLoginRequest.getCompanyCode()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("로그인 성공 객체정보 : {} ", authentication.toString());

        // JWT토큰 발급
        IssuedToken issuedToken = jwtTokenService.issueToken(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();
        return JwtLoginSuccessResponse.builder()
                .userId(appUser.getId())
                .username(userLoginRequest.getUsername())
                .companyCode(userLoginRequest.getCompanyCode())
                .issuedToken(issuedToken)
                .build();
    }


    @Override
    public void userLogout(CCodePrincipal cCodePrincipal) {
        // 인증 정보삭제
//        AppUser appUser = dbUserDetailService.loadUserById(cCodePrincipal.getId());

        // 유효한 access 토큰 블랙리스트에 저장
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String accessToken = token.split(" ")[1];
            jwtTokenService.removeAccessToken(accessToken);
            log.info("로그아웃으로 인한 access토큰 블랙리스트 추가 : {}", accessToken);
        }

        SecurityContextHolder.clearContext();
    }

    @Override
    public PINLoginResponse pinLogin(CCodePrincipal cCodePrincipal, PINLoginRequest pinLoginRequest) {
        AppUser user = dbUserDetailService.loadUserById(cCodePrincipal.getId());

        if (user.isLocked()) {
            throw new AuthException(AuthErrorCode.TOO_MANY_TRY_WRONG_LOGIN);
        }

        // 1. PIN 번호 일치 검증
        if (!passwordEncoder.matches(pinLoginRequest.getPin(), user.getPIN())) {
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

    @Override
    public void resetPin(CCodePrincipal cCodePrincipal, PINResetRequest pinResetRequest) {
        AppUser user = dbUserDetailService.loadUserById(cCodePrincipal.getId());
        user.changePin(passwordEncoder.encode(pinResetRequest.getNewPin()));
        userRepository.save(user);
    }
}
