package com.c104.seolo.domain.marker.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MarkerLocation {
    private Double locationX;
    private Double locationY;

    @Builder
    public MarkerLocation(Double locationX, Double locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }
}
