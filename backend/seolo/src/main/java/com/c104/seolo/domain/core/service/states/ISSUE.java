package com.c104.seolo.domain.core.service.states;

import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.domain.core.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ISSUE implements CodeState {
    private CoreTokenService coreTokenService;
    private LockerService lockerService;

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



        coreTokenService.issueCoreAuthToken(context.getAppUser(),)

        return CoreResponse.builder()
                .nextCode()
                .coreToken()
                .build();
    }
}
