package com.c104.seolo.domain.task.service.impl;

import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.service.MachineService;
import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.c104.seolo.domain.task.dto.TaskTemplateDto;
import com.c104.seolo.domain.task.dto.info.TaskHistoryInfo;
import com.c104.seolo.domain.task.dto.response.TaskHistoryResponse;
import com.c104.seolo.domain.task.dto.response.TaskListResponse;
import com.c104.seolo.domain.task.entity.TaskHistory;
import com.c104.seolo.domain.task.exception.TaskErrorCode;
import com.c104.seolo.domain.task.repository.TaskHistoryRepository;
import com.c104.seolo.domain.task.service.TaskHistoryService;
import com.c104.seolo.domain.task.service.TaskTemplateService;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import com.c104.seolo.global.security.service.DBUserDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskHistoryServiceImpl implements TaskHistoryService {
    private final TaskHistoryRepository taskHistoryRepository;
    private final TaskTemplateService taskTemplateService;
    private final DBUserDetailService dbUserDetailService;
    private final MachineService machineService;

    @Override
    public TaskHistoryResponse getTaskHistory(Long taskId, String companyCode) {
        TaskHistoryInfo taskHistoryInfo = taskHistoryRepository.getTaskHistoryInfoById(taskId);
        if (taskHistoryInfo == null) {
            throw new CommonException(TaskErrorCode.NOT_EXIST_TASK);
        }
        if (!taskHistoryInfo.getCompanyCode().equals(companyCode)) {
            throw new CommonException(TaskErrorCode.NOT_COMPANY_TASK);
        }
        return TaskHistoryResponse.builder()
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

    @Override
    public TaskHistoryDto getLatestTaskHistoryEntityByMachineId(Long machineId) {
        // 해당 장비에서 진행중인 작업내역을 조회한다.
        // 진행중인 작업내역이 없는 경우 조회할 수 없다.
        return TaskHistoryDto.of(taskHistoryRepository.getLatestTaskHistoryByMachineId(machineId)
                .orElseThrow(() -> new CommonException(TaskErrorCode.NOT_EXIST_TASK)));
    }

    @Override
    public TaskHistoryDto getCurrentTaskHistoryByMachineIdAndUserId(Long machineId, Long userId) {
        return TaskHistoryDto.of(taskHistoryRepository.getCurrentTaskHistoryByMachineIdAndUserId(machineId, userId)
                .orElseThrow(() -> new CommonException(TaskErrorCode.NOT_EXIST_TASK)));
    }

    @Override
    public void enrollTaskHistory(CCodePrincipal cCodePrincipal,
                                  Long taskTemplateId,
                                  Long machineId,
                                  String endTime,
                                  String taskPrecaution) {

        AppUser appUser = dbUserDetailService.loadUserById(cCodePrincipal.getId());

        TaskTemplateDto template = taskTemplateService.getTemplate(taskTemplateId);
        MachineDto machine = machineService.getMachineByMachineId(machineId);

        TaskHistory newTaskHistory = TaskHistory.builder()
                .user(appUser)
                .taskTemplate(template.toEntity())
                .machine(machine.toEntity())
                .taskEndEstimatedDateTime(LocalDateTime.parse(endTime))
                .taskCode(CODE.ISSUED)
                .taskPrecaution(taskPrecaution)
                .build();

        /*
        장비ID와 유저ID로 기존에 있던 모든 작업내역 DB를 조회하되 TASK_CODE 상태가 ISSUED, LOCKED 인 튜플이 있다면
        에러를 띄운다. → 이미 잠금로직 진행중이라는 뜻이니까
        */
        taskHistoryRepository.findByMachineIdOrUserIdAndTaskCode(machineId, appUser.getId())
                .ifPresent(taskHistory -> {
                    throw new CommonException(TaskErrorCode.ALREADY_LOCKING);
                });

        taskHistoryRepository.save(newTaskHistory);
    }

    @Override
    public TaskListResponse getTaskHistoryEntityByEmployeeNum(String employeeNum) {
        List<TaskHistoryInfo> taskHistoryInfos = taskHistoryRepository.getTaskHistoryByEmployeeNum(employeeNum);
        return TaskListResponse.builder()
                .tasks(taskHistoryInfos)
                .build();
    }

    @Override
    public void updateTaskCode(Long taskId, CODE taskCode) {
        updateTask(taskId, taskHistory -> taskHistory.setTaskCode(taskCode));
    }

    @Override
    public void updateTaskCodeNull(Long taskId) {
        updateTask(taskId, taskHistory -> taskHistory.setTaskCode(null));
    }

    @Override
    public TaskHistoryDto updateTaskEndTimeNow(Long taskId, LocalDateTime now) {
        TaskHistory updatedTaskHistory = updateTask(taskId, taskHistory -> taskHistory.setTaskEndDateTime(now));
        return TaskHistoryDto.of(updatedTaskHistory);
    }

    @Override
    public void updateTaskStartTimeNow(Long taskId, LocalDateTime now) {
        updateTask(taskId, taskHistory -> taskHistory.setTaskStartDateTime(now));
    }

    private TaskHistory updateTask(Long taskId, Consumer<TaskHistory> taskUpdater) {
        TaskHistory taskHistory = taskHistoryRepository.findById(taskId)
                .orElseThrow(() -> new CommonException(TaskErrorCode.NOT_EXIST_TASK));
        taskUpdater.accept(taskHistory);
        taskHistoryRepository.save(taskHistory);
        taskHistoryRepository.flush();
        return taskHistory;
    }
}
