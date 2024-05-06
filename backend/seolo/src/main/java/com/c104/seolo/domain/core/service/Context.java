package com.c104.seolo.domain.core.service;

import com.c104.seolo.domain.core.dto.request.CoreRequest;
import com.c104.seolo.domain.user.entity.AppUser;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Context {
    private AppUser appUser;
    private CodeState codeState;
    private String companyCode;
    private CoreRequest coreRequest;


    @Builder
    public Context(AppUser appUser, CodeState codeState, String companyCode, CoreRequest coreRequest) {
        this.appUser = appUser;
        this.codeState = codeState;
        this.companyCode = companyCode;
        this.coreRequest = coreRequest;
    }

    public void doLogic() {
        codeState.handle(this);
    }
}
