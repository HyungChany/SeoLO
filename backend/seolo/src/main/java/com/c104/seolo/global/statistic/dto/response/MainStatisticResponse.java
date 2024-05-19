package com.c104.seolo.global.statistic.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MainStatisticResponse {
    private Long NumAllMachinesInThisFacility;
    private Long NumAllLockersInThisCompany;
    private Long NumToodyTaskHistorieseInThisFacility;
    private Long NumThisWeekTaskHistorieseInThisFacility;
    private Long NumAllAccidentsInThisCompany;

    @Builder

    public MainStatisticResponse(Long numAllMachinesInThisFacility, Long numAllLockersInThisCompany, Long numToodyTaskHistorieseInThisFacility, Long numThisWeekTaskHistorieseInThisFacility, Long numAllAccidentsInThisCompany) {
        NumAllMachinesInThisFacility = numAllMachinesInThisFacility;
        NumAllLockersInThisCompany = numAllLockersInThisCompany;
        NumToodyTaskHistorieseInThisFacility = numToodyTaskHistorieseInThisFacility;
        NumThisWeekTaskHistorieseInThisFacility = numThisWeekTaskHistorieseInThisFacility;
        NumAllAccidentsInThisCompany = numAllAccidentsInThisCompany;
    }
}
