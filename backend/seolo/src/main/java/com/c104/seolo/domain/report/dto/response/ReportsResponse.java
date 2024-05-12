package com.c104.seolo.domain.report.dto.response;

import com.c104.seolo.domain.report.dto.ReportDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReportsResponse {
    List<ReportDto> reports;

    @Builder
    public ReportsResponse(List<ReportDto> reports) {
        this.reports = reports;
    }
}
