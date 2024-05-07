package com.c104.seolo.domain.task.dto;

import com.c104.seolo.domain.task.entity.TaskTemplate;
import com.c104.seolo.domain.task.enums.TaskType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public class TaskTemplateDto {
    private Long id;
    private TaskType taskType;
    private String precaution;

    @Builder
    public TaskTemplateDto(Long id, TaskType taskType, String precaution) {
        this.id = id;
        this.taskType = taskType;
        this.precaution = precaution;
    }

    public TaskTemplate toEntity() {
        return TaskTemplate.builder()
                .id(id)
                .taskType(taskType)
                .precaution(precaution)
                .build();
    }
    public static TaskTemplateDto of(TaskTemplate entity) {
        return TaskTemplateDto.builder()
                .id(entity.getId())
                .taskType(entity.getTaskType())
                .precaution(entity.getPrecaution())
                .build();
    }
}
