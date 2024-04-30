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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class SeoloLoginSuccessHandler implements AuthenticationSuccessHandler {
    private ObjectMapper objectMapper;

    @Autowired
    public SeoloLoginSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DaoCompanycodeToken authToken = (DaoCompanycodeToken) authentication;
        log.debug("인증성공객체 : {}", authToken);

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
