package com.c104.seolo.domain.core.controller;

import com.c104.seolo.domain.core.dto.response.LockerResponse;
import com.c104.seolo.domain.core.service.LockerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("locks")
@RequiredArgsConstructor
@Slf4j
public class LockerController {
    private final LockerService lockerService;

    @GetMapping
    public ResponseEntity<LockerResponse> getCompanyLockers(
            @RequestHeader("Company-Code") String companyCode
    ) {
        return ResponseEntity.ok(lockerService.getCompanyLockers(companyCode));
    }
}
