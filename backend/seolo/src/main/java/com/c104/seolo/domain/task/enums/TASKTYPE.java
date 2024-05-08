package com.c104.seolo.domain.task.enums;

import lombok.Getter;

@Getter
public enum TASKTYPE {
    FIX("수리"),
    REPAIR("정비"),
    CLEAN("청소"),
    ETC("기타");

    private final String selectTaskType;
    TASKTYPE(String selectTaskType) {
        this.selectTaskType = selectTaskType;
    }
}
