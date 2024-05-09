package com.c104.seolo.domain.report.service;

import com.c104.seolo.domain.report.dto.NewReport;
import com.c104.seolo.domain.report.dto.response.ReportsReponse;

public interface ReportService {
    void enrollReport(NewReport newReport);

    ReportsReponse getAllReports();
}
