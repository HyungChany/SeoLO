package com.c104.seolo.domain.task.dto;

import com.c104.seolo.domain.task.entity.TaskTemplate;
import com.c104.seolo.domain.task.enums.TASKTYPE;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RenameTaskTemplate {
    private Long taskTemplateId;
    private TASKTYPE taskTemplateName;
    private String taskPrecaution;

    @Builder
    public RenameTaskTemplate(Long taskTemplateId, TASKTYPE taskTemplateName, String taskPrecaution) {
        this.taskTemplateId = taskTemplateId;
        this.taskTemplateName = taskTemplateName;
        this.taskPrecaution = taskPrecaution;
    }


    public static RenameTaskTemplate of(TaskTemplate entity) {
        return RenameTaskTemplate.builder()
                .taskTemplateId(entity.getId())
                .taskTemplateName(entity.getTaskType())
                .taskPrecaution(entity.getPrecaution())
                .build();
    }
}
