package com.c104.seolo.domain.task.service.impl;

import com.c104.seolo.domain.task.dto.TaskTemplateDto;
import com.c104.seolo.domain.task.dto.mapper.TaskTemplateMapper;
import com.c104.seolo.domain.task.dto.response.TaskTemplateResponse;
import com.c104.seolo.domain.task.entity.TaskTemplate;
import com.c104.seolo.domain.task.exception.TaskTemplateErrorCode;
import com.c104.seolo.domain.task.repository.TaskTemplateRepository;
import com.c104.seolo.domain.task.service.TaskTemplateService;
import com.c104.seolo.global.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskTemplateServiceImpl implements TaskTemplateService {
    private final TaskTemplateRepository taskTemplateRepository;
    private final TaskTemplateMapper taskTemplateMapper;

    public TaskTemplateServiceImpl(TaskTemplateRepository taskTemplateRepository, TaskTemplateMapper taskTemplateMapper) {
        this.taskTemplateRepository = taskTemplateRepository;
        this.taskTemplateMapper = taskTemplateMapper;
    }

    @Override
    public TaskTemplateResponse getTemplates() {
        List<TaskTemplateDto> taskTemplates = taskTemplateRepository.findAllDto();
        return taskTemplateMapper.mapToResponse(taskTemplates);
    }

    @Override
    public TaskTemplate getTemplate(Long taskTemplateId) {
        return taskTemplateRepository.findById(taskTemplateId).orElseThrow(
                () -> new CommonException(TaskTemplateErrorCode.NOT_EXIST_TASK_TEMPLATE));
    }
}
