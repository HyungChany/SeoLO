package com.c104.seolo.global.security.config;

public class AuthorityConfig {

    public static String getHierarchy() {
        return "ROLE_MANAGER > ROLE_WORKER";
    }
}
