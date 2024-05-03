package com.c104.seolo.domain.machine.enums;

import lombok.Getter;

@Getter
public enum LockerType {
    NO("구분없음"),
    EL("전기 잠금장치"),
    SS("기동스위치 잠금장치"),
    GV("게이트벨브 잠금장치"),
    BV("볼벨브 잠금장치");


    private final String selectType;

    LockerType(String selectType) {
        this.selectType = selectType;
    }
}
