package com.c104.seolo.domain.task.service;

import com.c104.seolo.domain.task.dto.response.TaskTemplateResponse;
import com.c104.seolo.domain.task.entity.TaskTemplate;

public interface TaskTemplateService {
    TaskTemplateResponse getTemplates();
    TaskTemplate getTemplate(Long taskTemplateId);
}
