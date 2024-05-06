package com.c104.seolo.domain.core.service.states;

import com.c104.seolo.domain.core.service.CodeState;
import com.c104.seolo.domain.core.service.Context;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class INIT implements CodeState {
    @Override
    public void handle(Context context) {
        //현재로직없음
        log.info("INIT으로 요청 들어옴");
    }
}
