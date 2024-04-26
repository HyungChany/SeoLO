package com.c104.seolo.domain.facility.controller;

import com.c104.seolo.domain.facility.service.FacilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("facilities")
@RequiredArgsConstructor
@Slf4j
public class FacilityController {
    private final FacilityService facilityService;
}
