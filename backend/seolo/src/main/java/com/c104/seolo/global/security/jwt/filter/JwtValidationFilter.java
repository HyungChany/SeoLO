package com.c104.seolo.global.security.jwt.filter;


import com.c104.seolo.global.security.jwt.JwtUtils;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import com.c104.seolo.global.security.jwt.entity.JWTAuthenticationToken;
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
import org.springframework.util.StringUtils;
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

    @Autowired
    public JwtValidationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String[] tokenFromClient = StringUtils.split(request.getHeader(AUTHORIZATION_HEADER), BEARER_PREFIX);
        //authorizationHeader 가 null 이 아니고 authorizationHeader에 앞에 BEARER_PREFIX 가 있을 경우만 accessToken 값 있음, 아니면 null
        String accessToken = (tokenFromClient != null && tokenFromClient.length == 2 && tokenFromClient[0].isEmpty()) ? tokenFromClient[1] : null;

        if (accessToken == null) {
            doFilter(request, response, filterChain);
            return;
        }
        //엑세스 토큰 검증
         Authentication authentication = getAuthentication(jwtUtils.validateAccessToken(accessToken));

        // DB 접근 자주하는거 막기위해서 jwt토큰에서 파싱할 수 있는 정보만 context에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
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

