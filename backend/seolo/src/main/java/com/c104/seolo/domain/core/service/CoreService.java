package com.c104.seolo.domain.core.service;

import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;

public interface CoreService {

    void coreAuth(CCodePrincipal cCodePrincipal, String code);


}
