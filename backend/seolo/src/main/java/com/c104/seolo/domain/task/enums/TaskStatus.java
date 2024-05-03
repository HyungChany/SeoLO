package com.c104.seolo.domain.task.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
    ING("작업중"),
    END("작업완료");

    private final String selectTaskStatus;
    TaskStatus(String selectTaskStatus) {
        this.selectTaskStatus = selectTaskStatus;
    }
}
