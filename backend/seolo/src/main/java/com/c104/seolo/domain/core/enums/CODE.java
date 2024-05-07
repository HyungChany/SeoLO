package com.c104.seolo.domain.core.enums;

public enum CODE {
    // 상태코드
    INIT,
    LOCKED,
    ISSUED,
    // 행동코드
    CHECK,
    LOCK,
    UNLOCK,
    WRITE,
    ALERT
}
