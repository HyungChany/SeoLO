package com.c104.seolo.domain.task.service;

import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.c104.seolo.domain.task.dto.response.TaskHistoryResponse;
import com.c104.seolo.domain.task.dto.response.TaskListResponse;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;

public interface TaskHistoryService {
    TaskHistoryResponse getTaskHistory(Long taskId, String companyCode);
    TaskHistoryDto getLatestTaskHistoryEntityByMachineId(Long machineId);
    TaskHistoryDto getCurrentTaskHistoryByMachineIdAndUserId(Long machineId, Long userId);
    TaskListResponse getTaskHistoryEntityByEmployeeNum(String employeeNum);
    void enrollTaskHistory(CCodePrincipal cCodePrincipal,
                           Long taskTemplateId,
                           Long machineId,
                           String endTime,
                           String taskPrecaution);

    void updateTaskCode(Long taskId, CODE taskCode);
    void updateTaskCodeNull(Long taskId);
    void updateTaskEndTimeNow(Long taskId);
    void updateTaskStartTimeNow(Long taskId);
}
