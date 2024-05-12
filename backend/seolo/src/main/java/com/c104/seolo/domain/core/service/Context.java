package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.dto.request.CoreRequest;
import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Context {
    private CCodePrincipal cCodePrincipal;
    private CodeState codeState;
    private String companyCode;
    private CoreRequest coreRequest;

    @Builder
    public Context(CCodePrincipal cCodePrincipal, CodeState codeState, String companyCode, CoreRequest coreRequest) {
        this.cCodePrincipal = cCodePrincipal;
        this.codeState = codeState;
        this.companyCode = companyCode;
        this.coreRequest = coreRequest;
    }

    public CoreResponse doLogic() {
        return codeState.handle(this);
    }
}
