package com.c104.seolo.domain.task.service.impl;

import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.c104.seolo.domain.task.dto.info.TaskHistoryInfo;
import com.c104.seolo.domain.task.exception.TaskErrorCode;
import com.c104.seolo.domain.task.repository.TaskHistoryRepository;
import com.c104.seolo.domain.task.service.TaskHistoryService;
import com.c104.seolo.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskHistoryServiceImpl implements TaskHistoryService {
    private final TaskHistoryRepository taskHistoryRepository;

    @Override
    public TaskHistoryDto getTaskHistory(Long taskId, String companyCode) {
        TaskHistoryInfo taskHistoryInfo = taskHistoryRepository.getTaskHistoryInfoById(taskId);
        if (taskHistoryInfo == null) {
            throw new CommonException(TaskErrorCode.NOT_EXIST_TASK);
        }
        if (!taskHistoryInfo.getCompanyCode().equals(companyCode)) {
            throw new CommonException(TaskErrorCode.NOT_COMPANY_TASK);
        }
        return TaskHistoryDto.builder()
                .id(taskHistoryInfo.getId())
                .facilityName(taskHistoryInfo.getFacilityName())
                .machineName(taskHistoryInfo.getMachineName())
                .machineCode(taskHistoryInfo.getMachineCode())
                .workerTeam(taskHistoryInfo.getWorkerTeam())
                .workerName(taskHistoryInfo.getWorkerName())
                .workerTitle(taskHistoryInfo.getWorkerTitle())
                .taskType(taskHistoryInfo.getTaskType())
                .taskStartTime(taskHistoryInfo.getTaskStartTime())
                .taskEndTime(taskHistoryInfo.getTaskEndTime())
                .taskEndEstimatedTime(taskHistoryInfo.getTaskEndEstimatedTime())
                .taskPrecaution(taskHistoryInfo.getTaskPrecaution())
                .build();
    }
}
