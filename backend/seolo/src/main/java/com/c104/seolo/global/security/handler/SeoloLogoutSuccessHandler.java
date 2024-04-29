package com.c104.seolo.global.security.handler;

import com.c104.seolo.global.security.dto.response.LogoutSuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SeoloLogoutSuccessHandler implements LogoutSuccessHandler {
    private final ObjectMapper objectMapper;

    @Autowired
    public SeoloLogoutSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        LogoutSuccessResponse res = LogoutSuccessResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("로그아웃 성공 헤더의 Company-code를 지워주세요")
                .build();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(res));

        response.getWriter().flush();
//        response.getWriter().close();
    }
}
