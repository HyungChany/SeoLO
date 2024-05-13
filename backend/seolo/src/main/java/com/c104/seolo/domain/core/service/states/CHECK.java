package com.c104.seolo.domain.core.service.states;

import com.c104.seolo.domain.core.dto.request.CoreRequest;
import com.c104.seolo.domain.core.dto.response.CheckMoreResponse;
import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.domain.core.entity.Locker;
import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.core.service.CodeState;
import com.c104.seolo.domain.core.service.Context;
import com.c104.seolo.domain.core.service.LockerAccessLogService;
import com.c104.seolo.domain.core.service.LockerService;
import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.service.MachineService;
import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.c104.seolo.domain.task.service.TaskHistoryService;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import com.c104.seolo.global.security.service.DBUserDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CHECK implements CodeState {
    private final TaskHistoryService taskHistoryService;
    private final LockerAccessLogService lockerAccessLogService;
    private final LockerService lockerService;
    private final DBUserDetailService dbUserDetailService;
    private final MachineService machineService;

    @Override
    public CoreResponse handle(Context context) {
        /*
        행동코드 모듈 CHECK
        작업내역 정보를 응답한다.

        1. 장비ID를 이용해 작업내역TB 의 데이터 중 가장 최신 튜플을 조회한다.
        2. 전달받은 유저정보를 통해 자물쇠에 접근한 유저 정보를 접근기록DB에 기록한다.
        3. 1번에서 조회한 작업내역 데이터를 응답한다.
        */
        CoreRequest coreRequest = context.getCoreRequest();
        CCodePrincipal cCodePrincipal = context.getCCodePrincipal();
        // 1
        TaskHistoryDto latestTask = taskHistoryService.getLatestTaskHistoryEntityByMachineId(coreRequest.getMachineId());

        // 2
        Locker locker = lockerService.getLockerByUid(coreRequest.getLockerUid());
        lockerAccessLogService.recordAccessLog(cCodePrincipal, locker, CODE.CHECK);

        // 3
        return CoreResponse.builder()
                .nextCode(CODE.INIT)
                .taskHistory(latestTask)
                .checkMoreResponse(getMoreInfoForCHECK(cCodePrincipal, coreRequest))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private CheckMoreResponse getMoreInfoForCHECK(CCodePrincipal cCodePrincipal, CoreRequest coreRequest) {
        AppUser worker = dbUserDetailService.loadUserById(cCodePrincipal.getId());
        MachineDto machine = machineService.getMachineByMachineId(coreRequest.getMachineId());

        return CheckMoreResponse.builder()
                .workerName(worker.getEmployee().getEmployeeName())
                .workerTeam(worker.getUserTeam())
                .workerTitle(worker.getUserTitle())
                .facilityName(machine.getFacility().getFacilityName())
                .machineName(machine.getName())
                .machineNumber(machine.getNumber())
                .build();
    }
}
