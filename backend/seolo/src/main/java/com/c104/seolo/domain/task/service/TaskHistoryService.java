package com.c104.seolo.domain.task.service;

import com.c104.seolo.domain.task.dto.TaskHistoryDto;

public interface TaskHistoryService {
    TaskHistoryDto getTaskHistory(Long taskId, String companyCode);
}
