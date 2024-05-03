package com.c104.seolo.domain.task.dto.mapper;

import com.c104.seolo.domain.task.dto.TaskTemplateDto;
import com.c104.seolo.domain.task.dto.response.TaskTemplateResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TaskTemplateMapper {

    public TaskTemplateResponse mapToResponse(List<TaskTemplateDto> taskTemplates) {
        Map<String, List<String>> groupedTemplates = taskTemplates.stream()
                .collect(Collectors.groupingBy(
                        taskTemplateDto -> taskTemplateDto.getTaskType().toString(),
                        Collectors.mapping(TaskTemplateDto::getPrecaution, Collectors.toList())
                ));

        return TaskTemplateResponse.builder()
                .templates(groupedTemplates)
                .build();
    }
}

