package com.c104.seolo.domain.core.service.states;

import com.c104.seolo.domain.core.dto.request.CoreRequest;
import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.domain.core.exception.CoreTokenErrorCode;
import com.c104.seolo.domain.core.service.CodeState;
import com.c104.seolo.domain.core.service.Context;
import com.c104.seolo.domain.core.service.CoreTokenService;
import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.service.MachineService;
import com.c104.seolo.domain.report.dto.NewReport;
import com.c104.seolo.domain.report.service.ReportService;
import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.c104.seolo.domain.task.service.TaskHistoryService;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.global.security.service.DBUserDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UNLOCK implements CodeState {
    private final CoreTokenService coreTokenService;
    private final TaskHistoryService taskHistoryService;
    private final ReportService reportService;
    private final DBUserDetailService dbUserDetailService;
    private final MachineService machineService;

    @Override
    @Transactional
    public CoreResponse handle(Context context) {
        /*
        행동코드 모듈 UNLOCK
        외부 인증 토큰을 지우고 응답한다.
        프론트로부터 아래와 같은 데이터를 받는다.
        - 유저 정보
        - 장비 ID
        - 자물쇠고유넘버
        1. 아래 데이터를 통해 DB에서 튜플을 삭제한다.
            - 외부인증토큰(?)
            - 자물쇠고유넘버
        2. 해당 작업내역을 찾아
            TASK_CODE는 NULL로
            TASK_ENDTIME을 UNLOCK 요청이 들어온 시간으로 업데이트한다.
        3. 해당 작업내역을 보고서 DB에 저장한다.
        4. 204 No Content를 응답한다.
        */

        CoreRequest coreRequest = context.getCoreRequest();
        String tokenValue = coreRequest.getTokenValue();
        // 1
        // 토큰 검증
        if (!coreTokenService.validateTokenWithUser(tokenValue, context.getCCodePrincipal().getId())) {
            // 불일치 시 예외처리
            throw new CommonException(CoreTokenErrorCode.NOT_SAME_WITH_USER);
        }

        if (!coreTokenService.validateTokenWithLocker(tokenValue, coreRequest.getLockerUid())) {
            // 불일치 시 예외처리
            throw new CommonException(CoreTokenErrorCode.NOT_SAME_WITH_LOCKER);
        }
        // 토큰삭제
        coreTokenService.deleteTokenByTokenValue(tokenValue);

        // 2
        TaskHistoryDto updatedTaskhistory = syncTaskhistory(coreRequest);
        // 3
        createReport(updatedTaskhistory);
        return CoreResponse.builder() // 3
                .httpStatus(HttpStatus.NO_CONTENT)
                .message("자물쇠가 열림처리 되었습니다. 토큰이 삭제되었습니다. 진행했던 작업내역이 보고서로 저장됩니다. ")
                .build();
    }

    protected TaskHistoryDto syncTaskhistory(CoreRequest coreRequest) {
        TaskHistoryDto latestTaskHistory = taskHistoryService.getLatestTaskHistoryEntityByMachineId(coreRequest.getMachineId());
        taskHistoryService.updateTaskCodeNull(latestTaskHistory.getId());
        TaskHistoryDto updatedTaskHistory = taskHistoryService.updateTaskEndTimeNow(latestTaskHistory.getId(), LocalDateTime.now());
        return updatedTaskHistory;
    }

    protected void createReport(TaskHistoryDto updatedLatestTaskHistory) {
        MachineDto workedMachine = machineService.getMachineByMachineId(updatedLatestTaskHistory.getMachineId());
        AppUser worker = dbUserDetailService.loadUserById(updatedLatestTaskHistory.getId());

        NewReport newReport = NewReport.builder()
                .facilityName(workedMachine.getFacility().getFacilityName())
                .machineNumber(workedMachine.getNumber())
                .machineName(workedMachine.getName())
                .workerNumber(worker.getUsername())
                .workerName(worker.getEmployee().getEmployeeName())
                .workerTeam(worker.getEmployee().getEmployeeTeam())
                .workerTitle(worker.getEmployee().getEmployeeTitle())
                .tasktype(updatedLatestTaskHistory.getTaskTemplate().getTaskType())
                .taskStartDateTime(updatedLatestTaskHistory.getTaskStartDateTime())
                .taskEndDateTime(updatedLatestTaskHistory.getTaskEndDateTime())
                .build();

        reportService.enrollReport(newReport);
    }
}
