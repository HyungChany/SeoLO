package com.c104.seolo.domain.task.dto;

import com.c104.seolo.domain.task.enums.TaskType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
public class TaskTemplateDto {
    private Long id;
    private TaskType taskType;
    private String precaution;
}
