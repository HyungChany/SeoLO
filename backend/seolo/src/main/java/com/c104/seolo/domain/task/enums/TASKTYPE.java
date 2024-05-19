package com.c104.seolo.domain.task.enums;

import lombok.Getter;

@Getter
public enum TASKTYPE {
    수리("수리"),
    정비("정비"),
    청소("청소"),
    기타("기타");

    private final String selectTaskType;
    TASKTYPE(String selectTaskType) {
        this.selectTaskType = selectTaskType;
    }
}
