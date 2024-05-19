package com.c104.seolo.global.security.handler;

import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.domain.user.repository.UserRepository;
import com.c104.seolo.global.exception.AuthException;
import com.c104.seolo.global.security.dto.response.LoginSuccessResponse;
import com.c104.seolo.global.security.entity.DaoCompanycodeToken;
import com.c104.seolo.global.security.exception.AuthErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class SeoloLoginSuccessHandler implements AuthenticationSuccessHandler {
    private ObjectMapper objectMapper;
    private final SecurityContextRepository securityContextRepository;
    private final UserRepository userRepository;

    @Autowired
    public SeoloLoginSuccessHandler(SecurityContextRepository securityContextRepository, UserRepository userRepository, ObjectMapper objectMapper) {
        this.securityContextRepository = securityContextRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DaoCompanycodeToken authToken = (DaoCompanycodeToken) authentication;

        // isLocked 체크
        AppUser user = userRepository.findAppUserByEmployeeNum(authToken.getName())
                .orElseThrow(() -> new AuthException(AuthErrorCode.NOT_EXIST_APPUSER));
        if (user.isLocked()) {
            throw new AuthException(AuthErrorCode.TOO_MANY_TRY_WRONG_LOGIN);
        }

        log.debug("인증성공객체 successHandler 진입 : {}", authToken);
        log.debug("인증성공객체 successHandler 진입 : {}", authentication);

//        SecurityContext context = SecurityContextHolder.createEmptyContext();
        SecurityContext context = SecurityContextHolder.getContext();
        log.info("context 정보 1: {}", context);
        context.setAuthentication(authToken);
        log.info("context 정보 2: {}", context);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        LoginSuccessResponse res = LoginSuccessResponse.builder()
                .username(authToken.getName())
                .companyCode(authToken.getCompanyCode())
                .JSESSIONID(request.getSession(false).getId())
                .build();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(res));

        // HTTP 응답 커밋하여 필터 체인 종료
        response.getWriter().flush();
        response.getWriter().close();
    }

}
