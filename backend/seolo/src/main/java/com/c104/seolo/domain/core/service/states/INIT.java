package com.c104.seolo.domain.core.service.states;

import com.c104.seolo.domain.core.service.CodeState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class INIT implements CodeState {

    @Override
    public void handle(String code) {
        log.info("this is : {}", code);
    }
}
