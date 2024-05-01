package com.c104.seolo.domain.machine.controller;

import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.dto.response.MachineListResponse;
import com.c104.seolo.domain.machine.service.MachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("machines")
@RequiredArgsConstructor
@Slf4j
public class MachineController {
    private final MachineService machineService;

    @Secured("ROLE_MANAGER")
    @GetMapping("/{machineId}")
    public ResponseEntity<MachineDto> getMachine(
            @RequestHeader("Company-Code") String companyCode,
            @PathVariable Long machineId
    ) {
        return ResponseEntity.ok(machineService.findMachineByMachineId(companyCode, machineId));
    }

    @Secured("ROLE_MANAGER")
    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<MachineListResponse> getMachineList(
            @RequestHeader("Company-Code") String companyCode,
            @PathVariable("facilityId") Long facilityId
    ) {
        return ResponseEntity.ok(machineService.findMachineByCompanyAndFacility(companyCode, facilityId));
    }
}
