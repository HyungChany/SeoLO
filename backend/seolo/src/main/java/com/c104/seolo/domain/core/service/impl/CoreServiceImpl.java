package com.c104.seolo.domain.core.service.impl;

import com.c104.seolo.domain.core.service.Context;
import com.c104.seolo.domain.core.service.CoreService;
import org.springframework.stereotype.Service;

@Service
public class CoreServiceImpl implements CoreService {

    @Override
    public void coreAuth(String code) {
        Context context = new Context();
        State state;

        try {
            Class<?> clazz = Class.forName("package.path.to.states." + code + "State");
            state = (State) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            state = new DefaultState(); // 기본 상태 또는 오류 처리 상태
        }

        context.setState(state);
        context.request();
    }

}
