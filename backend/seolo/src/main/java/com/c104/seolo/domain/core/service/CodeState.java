package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.dto.response.CoreResponse;

public interface CodeState {
    CoreResponse handle(Context context);
}
