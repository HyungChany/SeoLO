package com.c104.seolo.domain.task.service.impl;

import com.c104.seolo.domain.task.dto.TaskTemplateDto;
import com.c104.seolo.domain.task.dto.mapper.TaskTemplateMapper;
import com.c104.seolo.domain.task.dto.response.TaskTemplateResponse;
import com.c104.seolo.domain.task.repository.TaskTemplateRepository;
import com.c104.seolo.domain.task.service.TaskTemplateService;
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
}
