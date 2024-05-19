package com.c104.seolo.global.statistic.service.impl;

import com.c104.seolo.domain.core.repository.LockerRepository;
import com.c104.seolo.domain.machine.repository.MachineRepository;
import com.c104.seolo.domain.report.repository.ReportRepository;
import com.c104.seolo.domain.task.repository.TaskHistoryRepository;
import com.c104.seolo.global.statistic.dto.response.MainStatisticResponse;
import com.c104.seolo.global.statistic.service.StatisticService;
import com.c104.seolo.global.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class StatisticServiceImpl implements StatisticService {

    private final MachineRepository machineRepository;
    private final LockerRepository lockerRepository;
    private final TaskHistoryRepository taskHistoryRepository;
    private final ReportRepository reportRepository;

    @Override
    public MainStatisticResponse getMainStatistics(Long facilityId, String companyCode) {
        return MainStatisticResponse.builder()
                .numAllMachinesInThisFacility(countAllMachinesByFacility(facilityId))
                .numAllLockersInThisCompany(countAllLockerByCompanyCode(companyCode))
                .numToodyTaskHistorieseInThisFacility(countTodayTaskHistoriesByFacilityId(facilityId))
                .numThisWeekTaskHistorieseInThisFacility(countWeekTaskHistorieseByFacilityId(facilityId))
                .numAllAccidentsInThisCompany(countThisMonthAccidentReports())
                .build();
    }

    @Override
    public Long countAllMachinesByFacility(Long facilityId) {
        return machineRepository.countByFacilityId(facilityId);
    }

    @Override
    public Long countAllLockerByCompanyCode(String companyCode) {
        return lockerRepository.countByCompanyCode(companyCode);
    }

    @Override
    public Long countTodayTaskHistoriesByFacilityId(Long facilityId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return taskHistoryRepository.countByFacilityIdAndCreatedAtBetween(facilityId, startOfDay, endOfDay);
    }

    @Override
    public Long countWeekTaskHistorieseByFacilityId(Long facilityId) {
        LocalDateTime startOfWeek = DateUtils.getStartOfWeek(LocalDate.now());
        LocalDateTime endOfWeek = DateUtils.getEndOfWeek(LocalDate.now());
        return taskHistoryRepository.countByFacilityIdAndCreatedAtBetween(facilityId, startOfWeek, endOfWeek);
    }

    @Override
    public Long countThisMonthAccidentReports() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1);
        return reportRepository.countByIsAccidentTrueAndCreatedAtBetween(startOfMonth, endOfMonth);
    }
}
