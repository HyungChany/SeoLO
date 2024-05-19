package com.c104.seolo.domain.task.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TaskHistoryAddRequest {
    private Long taskTemplateId;
    private String lockerUid;
    private Long machineId;
    private String taskPrecaution;
    private String endTime;
}
