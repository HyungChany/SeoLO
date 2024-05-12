package com.c104.seolo.domain.machine.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class ReportUpdateRequest {

    @NotNull
    private boolean isAccident;

    @NotNull
    private String accidentType;

    @NotNull(message = "Victims number cannot be null")
    @PositiveOrZero(message = "Victims number must be positive")
    private Integer victimsNum;

    @Builder
    public ReportUpdateRequest(boolean isAccident, String accidentType, Integer victimsNum) {
        this.isAccident = isAccident;
        this.accidentType = accidentType;
        this.victimsNum = victimsNum;
    }
}
