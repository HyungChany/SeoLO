package com.c104.seolo.domain.core.service.states;

import com.c104.seolo.domain.core.service.CodeState;
import com.c104.seolo.domain.core.service.Context;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UNLOCK implements CodeState {
    @Override
    public void handle(Context context) {
        log.info("this is : {}", context);
    }
}
