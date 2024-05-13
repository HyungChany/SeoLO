package com.c104.seolo.domain.core.service.states;

import com.c104.seolo.domain.core.dto.request.CoreRequest;
import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.domain.core.entity.Locker;
import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.core.service.CodeState;
import com.c104.seolo.domain.core.service.Context;
import com.c104.seolo.domain.core.service.LockerAccessLogService;
import com.c104.seolo.domain.core.service.LockerService;
import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.c104.seolo.domain.task.entity.TaskHistory;
import com.c104.seolo.domain.task.service.TaskHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CHECK implements CodeState {
    private final TaskHistoryService taskHistoryService;
    private final LockerAccessLogService lockerAccessLogService;
    private final LockerService lockerService;


    @Autowired
    public CHECK(TaskHistoryService taskHistoryService, LockerAccessLogService lockerAccessLogService, LockerService lockerService) {
        this.taskHistoryService = taskHistoryService;
        this.lockerAccessLogService = lockerAccessLogService;
        this.lockerService = lockerService;
    }

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

        // 1
        TaskHistoryDto latestTask = taskHistoryService.getLatestTaskHistoryEntityByMachineId(coreRequest.getMachineId());

        // 2
        Locker locker = lockerService.getLockerByUid(coreRequest.getLockerUid());
        lockerAccessLogService.recordAccessLog(context.getCCodePrincipal(), locker, CODE.CHECK);

        // 3
        return CoreResponse.builder()
                .nextCode(CODE.INIT)
                .taskHistory(latestTask)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
