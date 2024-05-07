package com.c104.seolo.domain.task.service;

import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.c104.seolo.domain.task.entity.TaskHistory;

public interface TaskHistoryService {
    TaskHistoryDto getTaskHistory(Long taskId, String companyCode);

    TaskHistory getLatestTaskHistoryEntityByMachineId(Long machineId);
}
