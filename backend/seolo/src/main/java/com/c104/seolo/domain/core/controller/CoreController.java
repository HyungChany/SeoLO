package com.c104.seolo.domain.core.controller;

import com.c104.seolo.domain.core.dto.request.CoreRequest;
import com.c104.seolo.domain.core.dto.response.CoreResponse;
import com.c104.seolo.domain.core.service.CoreService;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/core")
public class CoreController {

    private final CoreService coreService;

    @Autowired
    public CoreController(CoreService coreService) {
        this.coreService = coreService;
    }

    @PostMapping("/{code}")
    public CoreResponse coreAuthentication(@AuthenticationPrincipal CCodePrincipal cCodePrincipal,
                                           @RequestHeader("Company-Code") String companyCode,
                                           @PathVariable String code, @RequestBody CoreRequest coreRequest
    ) {
        return coreService.coreAuth(code, cCodePrincipal, companyCode, coreRequest);
    }
}
