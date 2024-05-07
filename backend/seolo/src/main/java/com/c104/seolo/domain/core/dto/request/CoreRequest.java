package com.c104.seolo.domain.core.dto.request;

import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
@Getter
public class CoreRequest {
    private final String lockerUid;
    private final TaskHistoryDto taskHistoryDto;
    private final Long machineId;

    @Builder
    public CoreRequest(String lockerUid, TaskHistoryDto taskHistoryDto, Long machineId) {
        this.lockerUid = lockerUid;
        this.taskHistoryDto = taskHistoryDto;
        this.machineId = machineId;
    }
}
