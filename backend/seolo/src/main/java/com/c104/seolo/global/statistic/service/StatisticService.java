package com.c104.seolo.global.statistic.service;

import com.c104.seolo.global.statistic.dto.response.MainStatisticResponse;

public interface StatisticService {

    MainStatisticResponse getMainStatistics(Long facilityId, String companyCode);
    Long countAllMachinesByFacility(Long facilityId);
    Long countAllLockerByCompanyCode(String companyCode);
    Long countTodayTaskHistoriesByFacilityId(Long facilityId);
    Long countWeekTaskHistorieseByFacilityId(Long facilityId);
    Long countThisMonthAccidentReports();
}
