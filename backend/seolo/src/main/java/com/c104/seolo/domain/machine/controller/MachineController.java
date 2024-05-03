package com.c104.seolo.domain.machine.controller;

import com.c104.seolo.domain.machine.dto.MachineDto;
import com.c104.seolo.domain.machine.dto.MachineSpaceDto;
import com.c104.seolo.domain.machine.dto.request.MachineRequest;
import com.c104.seolo.domain.machine.dto.response.MachineListResponse;
import com.c104.seolo.domain.machine.service.MachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
    @PostMapping
    public ResponseEntity<Void> createMachine(
            @RequestHeader("Company-Code") String companyCode,
            @RequestBody MachineRequest machineRequest
    ) {
        machineService.createMachine(machineRequest, companyCode);
        URI location = URI.create("/machines");
        return ResponseEntity.created(location).build();
    }

    @Secured("ROLE_MANAGER")
    @PatchMapping("/space")
    public ResponseEntity<Void> updateMachineSpace(
            @RequestBody List<MachineSpaceDto> machineSpaceRequest,
            @RequestHeader("Company-Code") String companyCode
    ) {
        machineService.updateMachineSpace(machineSpaceRequest, companyCode);
        Logger logger = LoggerFactory.getLogger(MachineController.class);
        logger.info("Received machine space update request: {}", machineSpaceRequest);
        return ResponseEntity.ok().build();
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
