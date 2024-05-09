package com.c104.seolo.domain.task.service.impl;

import com.c104.seolo.domain.task.dto.RenameTaskTemplate;
import com.c104.seolo.domain.task.dto.TaskTemplateDto;
import com.c104.seolo.domain.task.dto.response.RenameTaskTemplateResponse;
import com.c104.seolo.domain.task.entity.TaskTemplate;
import com.c104.seolo.domain.task.exception.TaskTemplateErrorCode;
import com.c104.seolo.domain.task.repository.TaskTemplateRepository;
import com.c104.seolo.domain.task.service.TaskTemplateService;
import com.c104.seolo.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskTemplateServiceImpl implements TaskTemplateService {
    private final TaskTemplateRepository taskTemplateRepository;

    @Override
    public RenameTaskTemplateResponse getTemplates() {
        List<TaskTemplate> taskTemplates = taskTemplateRepository.findAll();

        List<RenameTaskTemplate> renamedTemplates = new ArrayList<>();
        taskTemplates.forEach(template -> renamedTemplates.add(RenameTaskTemplate.of(template)));

        return RenameTaskTemplateResponse.builder()
                .templates(renamedTemplates)
                .build();
    }

    @Override
    public TaskTemplateDto getTemplate(Long taskTemplateId) {
        return TaskTemplateDto.of(taskTemplateRepository.findById(taskTemplateId).orElseThrow(
                () -> new CommonException(TaskTemplateErrorCode.NOT_EXIST_TASK_TEMPLATE)));
    }
}
