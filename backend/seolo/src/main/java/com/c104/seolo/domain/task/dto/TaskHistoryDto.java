package com.c104.seolo.domain.task.dto;

import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.task.entity.TaskHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskHistoryDto {
    private Long id;
    private Long userId;
    private TaskTemplateDto taskTemplate;
    private Long machineId;
    private String lockerUid;
    private LocalDateTime taskStartDateTime;
    private LocalDateTime taskEndDateTime;
    private LocalDateTime taskEndEstimatedDateTime;
    private CODE taskCode;
    private String taskPrecaution;

    @Builder
    public TaskHistoryDto(Long id, Long userId, TaskTemplateDto taskTemplate, Long machineId, String lockerUid, LocalDateTime taskStartDateTime, LocalDateTime taskEndDateTime, LocalDateTime taskEndEstimatedDateTime, CODE taskCode, String taskPrecaution) {
        this.id = id;
        this.userId = userId;
        this.taskTemplate = taskTemplate;
        this.machineId = machineId;
        this.lockerUid = lockerUid;
        this.taskStartDateTime = taskStartDateTime;
        this.taskEndDateTime = taskEndDateTime;
        this.taskEndEstimatedDateTime = taskEndEstimatedDateTime;
        this.taskCode = taskCode;
        this.taskPrecaution = taskPrecaution;
    }



    public static TaskHistoryDto of(TaskHistory taskHistory) {
        return TaskHistoryDto.builder()
                .id(taskHistory.getId())
                .userId(taskHistory.getUser().getId())
                .taskTemplate(TaskTemplateDto.of(taskHistory.getTaskTemplate()))
                .machineId(taskHistory.getMachine().getId())
                .lockerUid(taskHistory.getLocker().getUid())
                .taskStartDateTime(taskHistory.getTaskStartDateTime())
                .taskEndDateTime(taskHistory.getTaskEndDateTime())
                .taskEndEstimatedDateTime(taskHistory.getTaskEndEstimatedDateTime())
                .taskCode(taskHistory.getTaskCode())
                .taskPrecaution(taskHistory.getTaskPrecaution())
                .build();
    }
}