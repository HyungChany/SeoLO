package com.c104.seolo.global.security.jwt.filter;


import com.c104.seolo.global.exception.AuthException;
import com.c104.seolo.global.exception.SeoloErrorResponse;
import com.c104.seolo.global.security.jwt.JwtUtils;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import com.c104.seolo.global.security.jwt.entity.JWTAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtValidationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String DEVICE_TYPE_HEADER = "Device-Type"; // 기기종류 구분 헤더

    @Autowired
    public JwtValidationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        String deviceType = request.getHeader(DEVICE_TYPE_HEADER);
        String accessToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            accessToken = authorizationHeader.substring(BEARER_PREFIX.length());
        }

        log.info("Received accessToken: {}", accessToken);
        log.info("Received deviceType: {}", deviceType);

        if (accessToken == null || deviceType == null) {
            log.warn("Access token or device type is null");
            filterChain.doFilter(request, response);
            return;
        }

        //엑세스 토큰 검증
        try {
            Authentication authentication = getAuthentication(jwtUtils.validateAccessToken(accessToken, deviceType));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (AuthException e) {
            log.error("Token validation failed", e);
            SecurityContextHolder.clearContext();
            response.setStatus(e.getHttpStatus().value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(
                    SeoloErrorResponse.builder()
                            .httpStatus(e.getHttpStatus())
                            .errorCode(e.getErrorCode())
                            .message(e.getMessage())
                            .build()));
            return;
        }
    }


    private Authentication getAuthentication(Jws<Claims> claims) {
        String username = claims.getBody().getSubject();
        Long userId = claims.getBody().get("userId", Long.class);
        String companyCode = claims.getBody().get("companyCode", String.class);
        List<String> roles = claims.getBody().get("authorities", List.class);

        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new JWTAuthenticationToken(
                new CCodePrincipal(userId, username, companyCode),
                null,
                authorities
        );
    }
}

