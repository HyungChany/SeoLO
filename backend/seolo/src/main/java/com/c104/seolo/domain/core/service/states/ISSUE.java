package com.c104.seolo.domain.core.service.states;

import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.domain.core.entity.Token;
import com.c104.seolo.domain.core.service.CodeState;
import com.c104.seolo.domain.core.service.Context;
import com.c104.seolo.domain.core.service.CoreTokenService;
import com.c104.seolo.domain.task.service.TaskHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ISSUE implements CodeState {
    private final CoreTokenService coreTokenService;
    private final TaskHistoryService taskHistoryService;

    @Autowired
    public ISSUE(CoreTokenService coreTokenService, TaskHistoryService taskHistoryService) {
        this.coreTokenService = coreTokenService;
        this.taskHistoryService = taskHistoryService;
    }

    @Override
    public CoreResponse handle(Context context) {
        /*
        1. 백엔드 서버는 작업내역 데이터를 DB에 저장한다.
            - 상태는 ‘INIT’ 으로 저장한다. (아직 미잠금)
        2. 토큰을 발급 한다.
        3.  다음과 같은 데이터를 응답한다.
            - ‘LOCK’ 행동코드
            - 외부인증토큰
        */
        // 1. 작업내역 데이터 등록필요

        Token newToken = coreTokenService.issueCoreAuthToken(context.getAppUser(), context.getCoreRequest().getLockerUid()); // 2

        return CoreResponse.builder() // 3
                .nextCode("LOCK")
                .coreToken(newToken)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
