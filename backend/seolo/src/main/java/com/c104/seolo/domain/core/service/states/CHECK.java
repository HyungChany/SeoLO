package com.c104.seolo.domain.core.service.states;

import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.domain.core.service.CodeState;
import com.c104.seolo.domain.core.service.Context;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CHECK implements CodeState {

    @Override
    public CoreResponse handle(Context context) {
        /*
        행동코드 모듈 CHECK
        작업내역 정보를 응답한다.

        1. 장비ID를 이용해 작업내역TB 의 데이터 중 가장 최신 튜플을 조회한다.
        2. 전달받은 유저 SESSION 정보를 통해 자물쇠에 접근한 유저 정보를 DB에 기록한다.
        3. 1번에서 조회한 작업내역 데이터를 응답한다.
        */




        return null;
    }
}
