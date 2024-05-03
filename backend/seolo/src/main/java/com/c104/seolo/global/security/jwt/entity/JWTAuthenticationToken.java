package com.c104.seolo.global.security.jwt.entity;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private final String credentials;

    public JWTAuthenticationToken(Object principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);  // 인증 상태를 초기화
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
