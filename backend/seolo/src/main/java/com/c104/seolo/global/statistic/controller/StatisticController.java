package com.c104.seolo.global.statistic.controller;

import com.c104.seolo.global.statistic.dto.response.MainStatisticResponse;
import com.c104.seolo.global.statistic.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticService statisticService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/main/{facilityId}")
    public MainStatisticResponse getMainStatistics(@PathVariable Long facilityId,
                                                   @RequestHeader("Company-Code") String companyCode) {
        return statisticService.getMainStatistics(facilityId, companyCode);
    }
}
