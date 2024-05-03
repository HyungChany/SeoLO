package com.c104.seolo.domain.task.enums;

import lombok.Getter;

@Getter
public enum TaskType {
    FIX("수리"),
    REPAIR("정비"),
    CLEAN("청소"),
    ETC("기타");

    private final String selectTaskType;
    TaskType(String selectTaskType) {
        this.selectTaskType = selectTaskType;
    }
}
