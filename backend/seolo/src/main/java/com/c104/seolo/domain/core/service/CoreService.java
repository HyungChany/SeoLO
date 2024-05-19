package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.dto.request.CoreRequest;
import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;

public interface CoreService {

    CoreResponse coreAuth(String code,CCodePrincipal cCodePrincipal, String companyCode, CoreRequest coreRequest);
    CodeState setStateByReflection(String code);
    Context initContext(CodeState codeState, CCodePrincipal cCodePrincipal, String companyCode, CoreRequest coreRequest);


}
