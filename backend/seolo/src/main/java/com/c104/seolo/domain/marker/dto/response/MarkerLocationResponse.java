package com.c104.seolo.domain.marker.dto.response;

import com.c104.seolo.domain.marker.dto.MarkerLocation;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MarkerLocationResponse {
    private Long markerId;
    private MarkerLocation markerLocations;

    @Builder
    public MarkerLocationResponse(Long markerId, MarkerLocation markerLocations) {
        this.markerId = markerId;
        this.markerLocations = markerLocations;
    }
}
