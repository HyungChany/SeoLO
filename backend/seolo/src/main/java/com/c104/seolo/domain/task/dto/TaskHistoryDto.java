package com.c104.seolo.domain.task.dto;

import com.c104.seolo.domain.task.enums.TaskType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Getter
@Setter
public class TaskHistoryDto {
    @NotNull
    private Long id;
    @NotNull
    private String facilityName;
    @NotNull
    private String machineName;
    @NotNull
    private String machineCode;
    @NotNull
    private String workerTeam;
    @NotNull
    private String workerName;
    @NotNull
    private String workerTitle;
    @NotNull
    private TaskType taskType;
    @PastOrPresent
    private LocalDateTime taskStartTime;
    @FutureOrPresent
    private LocalDateTime taskEndTime;
    @FutureOrPresent
    private LocalDateTime taskEndEstimatedTime;
    private String taskPrecaution;
}
