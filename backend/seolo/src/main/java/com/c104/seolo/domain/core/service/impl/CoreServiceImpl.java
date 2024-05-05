package com.c104.seolo.domain.core.service.impl;

import com.c104.seolo.domain.core.entity.Token;
import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.core.service.CodeState;
import com.c104.seolo.domain.core.service.Context;
import com.c104.seolo.domain.core.service.CoreService;
import com.c104.seolo.domain.core.service.states.INIT;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class CoreServiceImpl implements CoreService {
    private static final String BASE_PACKAGE = "com.c104.seolo.domain.core.service.states.";
    @Override
    public void coreAuth(CCodePrincipal cCodePrincipal, String code) {
        Context context = new Context();
        CodeState state;

        log.info("초기 context : {}", context);

        try {
            CODE statusCode = CODE.valueOf(code); // code가 ENUM에 정의되어있는지 체크
            Class<?> clazz = Class.forName(BASE_PACKAGE + statusCode.name() );
            state = (CodeState) clazz.getDeclaredConstructor().newInstance();

            log.info("clazz : {}", clazz);
            log.info("state : {}", state);
        } catch (Exception e) {
            e.printStackTrace();
            state = new INIT(); // 기본 상태 또는 오류 처리 상태
        }
        context.setState(state);
        context.request();
    }


}
