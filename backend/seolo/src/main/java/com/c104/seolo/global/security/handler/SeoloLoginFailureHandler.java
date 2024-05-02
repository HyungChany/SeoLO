package com.c104.seolo.global.security.handler;

import com.c104.seolo.global.exception.AuthException;
import com.c104.seolo.global.exception.SeoloErrorResponse;
import com.c104.seolo.global.security.dto.response.LoginFailureResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class SeoloLoginFailureHandler implements AuthenticationFailureHandler {
    private ObjectMapper objectMapper;

    @Autowired
    public SeoloLoginFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        log.debug(exception.toString());

        if (exception instanceof AuthException) {
            // 사용자가 정의한 AuthException 처리
            AuthException e = (AuthException) exception;

            log.error("Authentication failed with status: {} error code: {} and message: {}",
                    e.getHttpStatus(), e.getErrorCode(), e.getMessage());

            SeoloErrorResponse errorRes = SeoloErrorResponse.builder()
                    .httpStatus(e.getHttpStatus())
                    .errorCode(e.getErrorCode())
                    .message(e.getMessage())
                    .build();

            response.getWriter().write(objectMapper.writeValueAsString(errorRes));
        } else {
            // 사용자가만든 AuthException 예외가 아니라면 스프링이 정해준 기본설정대로
            log.error("Authentication failure", exception);

            response.getWriter().write(objectMapper.writeValueAsString(
                    SeoloErrorResponse.builder()
                            .httpStatus(HttpStatus.UNAUTHORIZED)
                            .errorCode("AUTH_FAILURE")
                            .message(exception.getMessage())
                            .build()
                    )
            );
        }
    }
}
