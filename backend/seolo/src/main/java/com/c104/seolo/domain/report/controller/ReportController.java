package com.c104.seolo.domain.report.controller;

import com.c104.seolo.domain.machine.dto.request.ReportUpdateRequest;
import com.c104.seolo.domain.report.dto.ReportDto;
import com.c104.seolo.domain.report.dto.response.ReportsResponse;
import com.c104.seolo.domain.report.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_MANAGER")
    @GetMapping
    public ReportsResponse getAllReports() {
        return reportService.getAllReports();
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_MANAGER")
    @GetMapping("term")
    public ReportsResponse getReportsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return reportService.getReportsByDateRange(startDate, endDate);
    }

    @Secured("ROLE_MANAGER")
    @GetMapping("/{reportId}")
    public ReportDto getReport(@PathVariable Long reportId) {
        return reportService.getReport(reportId);
    }


    @Secured("ROLE_MANAGER")
    @PatchMapping("/{reportId}")
    public ReportDto updateReport(@PathVariable Long reportId, @Valid @RequestBody ReportUpdateRequest reportUpdateRequest) {
        return reportService.updateReport(reportId, reportUpdateRequest.isAccident(), reportUpdateRequest.getAccidentType(), reportUpdateRequest.getVictimsNum());
    }
}
