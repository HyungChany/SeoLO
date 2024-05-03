package com.c104.seolo.domain.task.service.impl;

import com.c104.seolo.domain.task.dto.TaskTemplateDto;
import com.c104.seolo.domain.task.dto.response.TaskTemplateResponse;
import com.c104.seolo.domain.task.repository.TaskTemplateRepository;
import com.c104.seolo.domain.task.service.TaskTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskTemplateServiceImpl implements TaskTemplateService {
    private final TaskTemplateRepository taskTemplateRepository;

    @Override
    public TaskTemplateResponse getTemplates() {
        List<TaskTemplateDto> taskTemplates = taskTemplateRepository.findAllDto();
        return TaskTemplateResponse.builder()
                .templates(taskTemplates)
                .build();
    }
}
