package com.c104.seolo.domain.task.enums;

import lombok.Getter;

@Getter
public enum TASKSTATUS {
    ING("작업중"),
    END("작업완료");

    private final String selectTaskStatus;
    TASKSTATUS(String selectTaskStatus) {
        this.selectTaskStatus = selectTaskStatus;
    }
}
