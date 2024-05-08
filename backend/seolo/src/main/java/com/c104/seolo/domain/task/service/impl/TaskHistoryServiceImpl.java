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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
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
        return TaskHistoryDto.of(taskHistoryRepository.getLatestTaskHistoryByMachineId(machineId)
                .orElseThrow(() -> new CommonException(TaskErrorCode.NOT_EXIST_TASK)));
    }

    @Override
    public void enrollTaskHistory(CCodePrincipal cCodePrincipal,
                                  Long taskTemplateId,
                                  Long machineId,
                                  String endTime,
                                  String taskPrecaution) {


        AppUser appUser = dbUserDetailService.loadUserById(cCodePrincipal.getId());

//        1. AppUserDto정보를 바탕으로 새로운 AppUser라는 객체를 만들어서 넣는건가?
//        -> 근데 이러면 정보가 다를 수 있잖아 AppUserDto가 AppUser 객체를 만드는데
//        -> 충분한 데이터를 안보낼 수도 있잖아
//        -> 충분한 데이터를 가지고 있어도 둘이 같은건가? DB에서 바로 온 애가아닌데
//        -> 그렇게 만든 새로운 AppUser를 수정하고 save하면 먹힘? -> save하려면 얘가 어차피 UserRepository 의존해야하잖아

//        2. 그러면 UserRepositotory에서 AppUserDto에 있는 뭐 식별자 같은걸로
//        -> 유저 정보를 조회해서 객체로 받아왔어
//        -> 그러면 유저가아닌 다른 도메인에서 UserRepository를 의존받아서 조회해야하는거잖아?
//        -> 그렇다면 왜 굳이 조회하는 서비스 메서드를 만든거임? 애초에 걍 처음부터 그 메서드에서 조회를하지
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
        taskHistoryRepository.findByMachineIdAndUserIdOrTaskCode(machineId, appUser.getId())
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
}
