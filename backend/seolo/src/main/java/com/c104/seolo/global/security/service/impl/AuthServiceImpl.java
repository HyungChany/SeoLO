package com.c104.seolo.global.security.service.impl;

import com.c104.seolo.domain.core.dto.TokenDto;
import com.c104.seolo.domain.core.service.CoreTokenService;
import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.c104.seolo.domain.task.service.TaskHistoryService;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final CoreTokenService coreTokenService;
    private final DBUserDetailService dbUserDetailService;
    private final TaskHistoryService taskHistoryService;

    @Override
    public JwtLoginSuccessResponse userLogin(UserLoginRequest userLoginRequest, String deviceType) {
        log.info("header : {}", deviceType);
        Authentication authentication = authenticationManager.authenticate(new DaoCompanycodeToken(
                userLoginRequest.getUsername(),
                userLoginRequest.getPassword(),
                userLoginRequest.getCompanyCode()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("로그인 성공 객체정보 : {} ", authentication.toString());

        // JWT토큰 발급
        IssuedToken issuedToken = jwtTokenService.issueToken(authentication, deviceType);
        log.debug("발급된 토큰 : {}", issuedToken.toString());

        AppUser appUser = (AppUser) authentication.getPrincipal();
        TokenDto coreToken = coreTokenService.getCoreTokenByUserIdIfNotNull(appUser.getId());

        String workingLockerUid = null;
        Long workingMachineId = null;
        String isseudCoreToken = null;
        if (coreToken != null) {
            workingLockerUid = coreToken.getLocker().getUid();
            isseudCoreToken = coreToken.getTokenValue();

            TaskHistoryDto nowTask = taskHistoryService.getCurrentTaskHistoryByLockerIdAndUserIdIfNotNull(coreToken.getLocker().getId(), appUser.getId());
            workingMachineId = (nowTask != null) ? nowTask.getMachineId() : null;
        }

        return JwtLoginSuccessResponse.builder()
                .userId(appUser.getId())
                .username(userLoginRequest.getUsername())
                .companyCode(userLoginRequest.getCompanyCode())
                .issuedToken(issuedToken)
                .codeStatus(appUser.getStatusCODE().name())
                .workingLockerUid(workingLockerUid)
                .workingMachineId(workingMachineId)
                .issuedCoreToken(isseudCoreToken)
                .build();
    }


    @Override
    public void userLogout(CCodePrincipal cCodePrincipal) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String accessToken = token.split(" ")[1];
            jwtTokenService.removeAccessToken(accessToken);
            log.debug("로그아웃으로 인한 access토큰 블랙리스트 추가 : {}", accessToken);
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
