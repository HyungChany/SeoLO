package com.c104.seolo.domain.core.service.states;

import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.domain.core.service.CodeState;
import com.c104.seolo.domain.core.service.Context;
import com.c104.seolo.domain.core.service.LockerAccessLogService;
import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.service.MachineService;
import com.c104.seolo.domain.task.entity.TaskHistory;
import com.c104.seolo.domain.task.service.TaskHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CHECK implements CodeState {
    private final TaskHistoryService taskHistoryService;
    private final LockerAccessLogService lockerAccessLogService;
    private final MachineService machineService;

    @Autowired
    public CHECK(TaskHistoryService taskHistoryService, LockerAccessLogService lockerAccessLogService, MachineService machineService) {
        this.taskHistoryService = taskHistoryService;
        this.lockerAccessLogService = lockerAccessLogService;
        this.machineService = machineService;
    }

    @Override
    public CoreResponse handle(Context context) {
        /*
        행동코드 모듈 CHECK
        작업내역 정보를 응답한다.

        1. 장비ID를 이용해 작업내역TB 의 데이터 중 가장 최신 튜플을 조회한다.
        2. 전달받은 유저 SESSION 정보를 통해 자물쇠에 접근한 유저 정보를 DB에 기록한다.
        3. 1번에서 조회한 작업내역 데이터를 응답한다.
        */
        TaskHistory latestTaskHistory = taskHistoryService.getLatestTaskHistoryEntityByMachineId(context.getCoreRequest().getMachineId()); // 1

        // machinedto말고 machine객체로
        MachineDto machine = machineService.findMachineByMachineId(context.getCompanyCode(), context.getCoreRequest().getMachineId());


//        lockerAccessLogService.recordAccessLog(context.getAppUser(), Machine machine);

        return null;
    }
}
