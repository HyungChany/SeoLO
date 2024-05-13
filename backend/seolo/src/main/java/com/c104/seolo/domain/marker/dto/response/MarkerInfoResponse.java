package com.c104.seolo.domain.marker.dto.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class MarkerInfoResponse {
    private String workerName;
    private String estimatedEndTime;
    private String content;

    @Builder
    public MarkerInfoResponse(String workerName, String estimatedEndTime, String content) {
        this.workerName = workerName;
        this.estimatedEndTime = estimatedEndTime;
        this.content = content;
    }
}
