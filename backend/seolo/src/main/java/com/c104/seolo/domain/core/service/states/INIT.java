package com.c104.seolo.domain.core.service.states;

import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.domain.core.service.CodeState;
import com.c104.seolo.domain.core.service.Context;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class INIT implements CodeState {
    @Override
    public CoreResponse handle(Context context) {
        return null;
    }
}
