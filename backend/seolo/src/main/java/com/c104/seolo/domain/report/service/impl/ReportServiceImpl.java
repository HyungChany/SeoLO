package com.c104.seolo.domain.report.service.impl;

import com.c104.seolo.domain.report.dto.NewReport;
import com.c104.seolo.domain.report.dto.ReportDto;
import com.c104.seolo.domain.report.dto.response.ReportsReponse;
import com.c104.seolo.domain.report.entity.Report;
import com.c104.seolo.domain.report.exception.ReportErrorCode;
import com.c104.seolo.domain.report.repository.ReportRepository;
import com.c104.seolo.domain.report.service.ReportService;
import com.c104.seolo.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    @Override
    public void enrollReport(NewReport newReport) {
        // 작업이 끝난 작업내역을
        // 보고서로 DB에 저장한다.
        // 해당 보고서는 어떠한 필드와도 연관관계를 맺고있지않고
        // 다른 테이블의 변화와 별도로 작업이 끝난 그 순간의 기록만을 남긴다.

        Report report = newReport.toEntity();
        reportRepository.save(report);
    }

    @Override
    public ReportsReponse getAllReports() {
        List<Report> allReports = reportRepository.findAll();

        Collections.reverse(allReports);
        List<ReportDto> reportDtos = new ArrayList<>();
        allReports.forEach(report -> reportDtos.add(ReportDto.of(report)));

        return ReportsReponse.builder()
                .reports(reportDtos)
                .build();
    }

    @Override
    public ReportDto getReport(Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(
                () -> new CommonException(ReportErrorCode.NOT_EXIST_REPORT)
        );

        return ReportDto.of(report);
    }

    @Override
    public ReportDto updateReport(Long reportId, boolean isAccident, String accidentType, Integer victimsNum) {
        Report report = reportRepository.findById(reportId).orElseThrow(
                () -> new CommonException(ReportErrorCode.NOT_EXIST_REPORT));

        report.updateReportDetails(isAccident, accidentType, victimsNum);
        reportRepository.save(report);
        return ReportDto.of(report);
    }
}
