package com.c104.seolo.domain.report.service;

import com.c104.seolo.domain.report.dto.NewReport;
import com.c104.seolo.domain.report.dto.ReportDto;
import com.c104.seolo.domain.report.dto.response.ReportsResponse;
import com.c104.seolo.domain.report.entity.Report;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    void enrollReport(NewReport newReport);
    ReportsResponse getAllReports();
    ReportDto getReport(Long reportId);
    ReportDto updateReport(Long reportId, boolean isAccident, String accidentType, Integer victimsNum);

    ReportsResponse getReportsByDateRange(LocalDate startDate, LocalDate endDate);
}
