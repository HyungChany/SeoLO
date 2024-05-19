package com.c104.seolo.domain.marker.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
public class AddMarkerRequest {
    private Long machineId;
    private Double markerX;
    private Double markerY;
}
