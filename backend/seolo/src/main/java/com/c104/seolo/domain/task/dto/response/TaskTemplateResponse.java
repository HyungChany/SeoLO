package com.c104.seolo.domain.task.dto.response;

import com.c104.seolo.domain.task.dto.TaskTemplateDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@ToString
public class TaskTemplateResponse {
    private List<TaskTemplateDto> templates;
}
