package com.c104.seolo.global.security.exception;

import com.c104.seolo.global.exception.AuthException;
import com.c104.seolo.global.exception.SeoloErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());

        HttpStatus status;
        String errorCode;
        String message;

        if (authException instanceof AuthException) {
            AuthException e = (AuthException) authException;
            status = e.getHttpStatus();
            errorCode = e.getErrorCode();
            message = e.getMessage();
        } else {
            status = HttpStatus.UNAUTHORIZED;
            errorCode = "UNAUTHORIZED";
            message = "Full authentication is required to access this resource";
        }

        SeoloErrorResponse errorResponse = SeoloErrorResponse.builder()
                .httpStatus(status)
                .errorCode(errorCode)
                .message(message)
                .build();

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
