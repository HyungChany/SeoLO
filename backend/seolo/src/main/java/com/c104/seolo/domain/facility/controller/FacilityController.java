package com.c104.seolo.domain.facility.controller;

import com.c104.seolo.domain.facility.dto.request.FacilityRequest;
import com.c104.seolo.domain.facility.dto.response.FacilityResponse;
import com.c104.seolo.domain.facility.service.FacilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("facilities")
@RequiredArgsConstructor
@Slf4j
public class FacilityController {
    private final FacilityService facilityService;

    @GetMapping
    public ResponseEntity<FacilityResponse> getFacilities(
            @RequestHeader("Company-Code") String companyCode
    ) {
        return ResponseEntity.ok(facilityService.findFacilityByCompany(companyCode));
    }

    @PostMapping
    public ResponseEntity<Void> createFacility(
            @RequestHeader("Company-Code") String companyCode,
            @RequestBody FacilityRequest facilityRequest
    ) {
        facilityService.createFacility(facilityRequest, companyCode);
        URI location = URI.create("/facilities");
        return ResponseEntity.created(location).build();
    }
}
