package com.c104.seolo.domain.core.controller;

import com.c104.seolo.domain.core.dto.request.LockerEnrollRequest;
import com.c104.seolo.domain.core.dto.request.LockerRequest;
import com.c104.seolo.domain.core.dto.response.LockerResponse;
import com.c104.seolo.domain.core.service.LockerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("locks")
@RequiredArgsConstructor
@Slf4j
public class LockerController {
    private final LockerService lockerService;


    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ROLE_MANAGER")
    @PostMapping
    public void enrollLocker(@RequestHeader("Company-Code") String companyCode, @Valid @RequestBody LockerEnrollRequest lockerEnrollRequest) {
        lockerService.enrollLocker(companyCode, lockerEnrollRequest);
    }

    @Secured("ROLE_MANAGER")
    @GetMapping
    public ResponseEntity<LockerResponse> getCompanyLockers(
            @RequestHeader("Company-Code") String companyCode
    ) {
        return ResponseEntity.ok(lockerService.getCompanyLockers(companyCode));
    }

    @PatchMapping("/{lockId}")
    public ResponseEntity<Void> updateCompanyLocker(
            @RequestHeader("Company-Code") String companyCode,
            @RequestBody LockerRequest lockerRequest,
            @PathVariable("lockId") Long lockId
    ) {
        lockerService.updateLocker(lockerRequest, companyCode, lockId);
        return ResponseEntity.accepted().build();
    }


}
