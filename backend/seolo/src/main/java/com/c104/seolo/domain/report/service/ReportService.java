package com.c104.seolo.domain.report.service;

import com.c104.seolo.domain.report.dto.NewReport;
import com.c104.seolo.domain.report.dto.ReportDto;
import com.c104.seolo.domain.report.dto.response.ReportsResponse;

public interface ReportService {
    void enrollReport(NewReport newReport);
    ReportsResponse getAllReports();
    ReportDto getReport(Long reportId);
    ReportDto updateReport(Long reportId, boolean isAccident, String accidentType, Integer victimsNum);
}
