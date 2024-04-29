package com.c104.seolo.domain.machine.enums;

import lombok.Getter;

@Getter
public enum Role {
    Main("정"),
    Deputy("부");

    private final String selectRole;
    Role(String role) {
        this.selectRole = role;
    }
}
