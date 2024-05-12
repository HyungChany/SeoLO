package com.c104.seolo.domain.core.service.impl;

import com.c104.seolo.domain.core.dto.request.CoreRequest;
import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.core.exception.CoreErrorCode;
import com.c104.seolo.domain.core.service.CodeState;
import com.c104.seolo.domain.core.service.Context;
import com.c104.seolo.domain.core.service.CoreService;
import com.c104.seolo.domain.core.service.LockerService;
import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CoreServiceImpl implements CoreService {

    private static final String BASE_PACKAGE = "com.c104.seolo.domain.core.service.states.";
    private final ApplicationContext applicationContext;
    private final LockerService lockerService;


    @Override
    public CoreResponse coreAuth(String code, CCodePrincipal cCodePrincipal, String companyCode, CoreRequest coreRequest) {
        /*
        사용자로부터 받은 code 값에 따라 다른 로직을 수행
        */
        CodeState codeState = setStateByReflection(code);
        Context context = initContext(codeState, cCodePrincipal, companyCode, coreRequest);
        if (coreRequest.getBatteryInfo() != null && coreRequest.getLockerUid() != null) {
            // 배터리 정보가 오는 경우 배터리 자물쇠의 배터리 업데이트
            lockerService.updateBatteryByLockerUid(coreRequest.getLockerUid(), coreRequest.getBatteryInfo());
        }

        CoreResponse coreResponse = context.doLogic();
        return coreResponse;
    }

    @Override
    public Context initContext(CodeState codeState, CCodePrincipal cCodePrincipal, String companyCode, CoreRequest coreRequest) {
        /*
        실행할 클래스를 구분하는 CodeState 및 로직에 필요한 정보들을 담아 Context를 초기화하는 메서드
        */

        return Context.builder()
                .cCodePrincipal(cCodePrincipal)
                .codeState(codeState)
                .companyCode(companyCode)
                .coreRequest(coreRequest)
                .build();
    }

    @Override
    public CodeState setStateByReflection(String code) {
        /*
        문자열 code에 해당하는 CodeState 구현체를 인스턴스로 만들어 리턴
        */

        CodeState state;

        try {
            CODE statusCode = CODE.valueOf(code); // code가 ENUM에 정의되어있는지 체크
            Class<?> clazz = Class.forName(BASE_PACKAGE + statusCode.name() );
//            state = (CodeState) clazz.getDeclaredConstructor().newInstance();
            state = (CodeState) applicationContext.getBean(clazz);

            log.info("clazz : {}", clazz);
            log.info("state : {}", state);
        } catch (Exception e) {
            throw new CommonException(CoreErrorCode.STATE_REFLECTION_ERROR);
        }
        return state;
    }
}
