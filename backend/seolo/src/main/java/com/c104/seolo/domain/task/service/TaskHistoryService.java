package com.c104.seolo.domain.task.service;

import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.c104.seolo.domain.task.dto.request.TaskHistoryAddRequest;
import com.c104.seolo.domain.task.entity.TaskHistory;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;

public interface TaskHistoryService {
    TaskHistoryDto getTaskHistory(Long taskId, String companyCode);
    TaskHistory getLatestTaskHistoryEntityByMachineId(Long machineId);
    void enrollTaskHistory(CCodePrincipal cCodePrincipal, Long taskTemplateId, Long machineId, String endTime,String taskPrecaution);
}
