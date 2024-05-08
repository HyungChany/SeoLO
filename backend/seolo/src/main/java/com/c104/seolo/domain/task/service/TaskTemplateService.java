package com.c104.seolo.domain.task.service;

import com.c104.seolo.domain.task.dto.TaskTemplateDto;
import com.c104.seolo.domain.task.dto.response.TaskTemplateResponse;
import com.c104.seolo.domain.task.entity.TaskTemplate;

public interface TaskTemplateService {
    TaskTemplateResponse getTemplates();
    TaskTemplateDto getTemplate(Long taskTemplateId);
}
