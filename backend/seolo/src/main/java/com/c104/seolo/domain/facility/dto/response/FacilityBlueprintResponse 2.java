package com.c104.seolo.domain.facility.dto.response;

import com.c104.seolo.domain.marker.dto.response.MarkerLocationResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FacilityBlueprintResponse {
    private String blueprintURL;
    List<MarkerLocationResponse> markers;

    @Builder
    public FacilityBlueprintResponse(String blueprintURL, List<MarkerLocationResponse> markers) {
        this.blueprintURL = blueprintURL;
        this.markers = markers;
    }
}
