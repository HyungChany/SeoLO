package com.c104.seolo.domain.task.service;

import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.c104.seolo.domain.task.dto.response.TaskHistoryResponse;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;

public interface TaskHistoryService {
    TaskHistoryResponse getTaskHistory(Long taskId, String companyCode);
    TaskHistoryDto getLatestTaskHistoryEntityByMachineId(Long machineId);
    void enrollTaskHistory(CCodePrincipal cCodePrincipal, Long taskTemplateId, Long machineId, String endTime,String taskPrecaution);
}
