package com.c104.seolo.domain.core.service.states;

import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.domain.core.service.CodeState;
import com.c104.seolo.domain.core.service.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class UNLOCK implements CodeState {

    @Override
    public CoreResponse handle(Context context) {
        /*
        행동코드 모듈 UNLOCK
        외부 인증 토큰을 지우고 응답한다.
        1. 아래 데이터를 통해 DB에서 튜플을 삭제한다.
            - 외부인증토큰
            - 자물쇠고유넘버
        2. 필요한 처리를 한다.
        3. 204 No Content를 응답한다.
        */

        

        return CoreResponse.builder()
                .httpStatus(HttpStatus.NO_CONTENT)
                .build();
    }
}
