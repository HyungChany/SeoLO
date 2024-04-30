package com.c104.seolo.global.security.handler;

import com.c104.seolo.global.security.dto.response.AuthSuccessResponse;
import com.c104.seolo.global.security.entity.DaoCompanycodeToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class SeoloLoginSuccessHandler implements AuthenticationSuccessHandler {
    private ObjectMapper objectMapper;
    private final SecurityContextRepository securityContextRepository;

    @Autowired
    public SeoloLoginSuccessHandler(SecurityContextRepository securityContextRepository, ObjectMapper objectMapper) {
        this.securityContextRepository = securityContextRepository;
        this.objectMapper = objectMapper;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DaoCompanycodeToken authToken = (DaoCompanycodeToken) authentication;
        log.debug("인증성공객체 successHandler 진입 : {}", authToken);
        log.debug("인증성공객체 successHandler 진입 : {}", authentication);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        log.debug("context 정보 1: {}", context);
        context.setAuthentication(authToken);
        log.debug("context 정보 2: {}", context);
        SecurityContextHolder.setContext(context);
//        RequestContextHolder.currentRequestAttributes().setAttribute("SPRING_SECURITY_CONTEXT", context, RequestAttributes.SCOPE_SESSION);
        securityContextRepository.saveContext(context, request, response);

        AuthSuccessResponse res = AuthSuccessResponse.builder()
                .username(authToken.getName())
                .companyCode(authToken.getCompanyCode())
                .JSESSIONID(request.getSession(true).getId())
                .build();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(res));

        // HTTP 응답 커밋하여 필터 체인 종료
        response.getWriter().flush();
        response.getWriter().close();
    }
}
