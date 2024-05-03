package com.c104.seolo.global.security.jwt.entity;

import lombok.Getter;

@Getter
public class CCodePrincipal {
    private Long id;
    private String username;
    private String companyCode;

    public CCodePrincipal(Long id, String username, String companyCode) {
        this.id = id;
        this.username = username;
        this.companyCode = companyCode;
    }
}

