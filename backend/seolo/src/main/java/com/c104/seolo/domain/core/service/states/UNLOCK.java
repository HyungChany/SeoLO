package com.c104.seolo.domain.core.service.states;

import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.domain.core.service.CodeState;
import com.c104.seolo.domain.core.service.Context;
import com.c104.seolo.domain.core.service.CoreTokenService;
import com.c104.seolo.domain.task.service.TaskHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UNLOCK implements CodeState {
    private final CoreTokenService coreTokenService;
    private final TaskHistoryService taskHistoryService;

    @Override
    public CoreResponse handle(Context context) {
        /*
        행동코드 모듈 UNLOCK
        외부 인증 토큰을 지우고 응답한다.
        1. 아래 데이터를 통해 DB에서 튜플을 삭제한다.
            - 외부인증토큰
            - 자물쇠고유넘버
        2. 해당 작업내역을 찾아
            TASK_CODE는 NULL로
            TASK_ENDTIME을 UNLOCK 요청이 들어온 시간으로 업데이트한다.
        3. 204 No Content를 응답한다.
        */

        // 1
        coreTokenService.deleteTokenByUserId(context.getCCodePrincipal().getId());

        // 2


        // 3
        return CoreResponse.builder() // 3
                .httpStatus(HttpStatus.NO_CONTENT)
                .message("등록된 인증토큰이 삭제되었습니다.")
                .build();
    }
}
