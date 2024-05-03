package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.service.states.INIT;

public class Context {
    private CodeState codeState;

    public Context() {
        this.codeState = new INIT(); // 초기상태설정
    }

    public void setState(CodeState newState) {
        this.codeState = newState;
    }

    public void request() {
        codeState.handle(this);
    }
}
