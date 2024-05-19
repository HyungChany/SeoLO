package com.c104.seolo.global.security.handler;

import com.c104.seolo.global.exception.AuthException;
import com.c104.seolo.global.exception.SeoloErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Access denied error: {}", accessDeniedException.getMessage());

        SeoloErrorResponse errorResponse = SeoloErrorResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .errorCode("ACCESS_DENIED")
                .message("해당 사용자의 권한으로는 접근할 수 없습니다.")
                .build();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
