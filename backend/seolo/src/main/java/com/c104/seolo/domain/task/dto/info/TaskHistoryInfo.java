package com.c104.seolo.domain.task.dto.info;

import com.c104.seolo.domain.task.enums.TaskStatus;
import com.c104.seolo.domain.task.enums.TaskType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TaskHistoryInfo {
    private Long id;
    private TaskType taskType;
    private LocalDateTime taskStartTime;
    private LocalDateTime taskEndTime;
    private LocalDateTime taskEndEstimatedTime;
    private String taskPrecaution;
    private String companyCode;
    private String facilityName;
    private String machineName;
    private String machineCode;
    private String workerTeam;
    private String workerName;
    private String workerTitle;
}
