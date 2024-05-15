package com.c104.seolo.domain.marker.dto;

import com.c104.seolo.domain.marker.entity.Marker;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MarkerDto {
    private Long id;
    private Long facilityId;
    private Long machineId;
    private Double locationX;
    private Double locationY;

    public static MarkerDto of(Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("Marker cannot be null");
        }
        return MarkerDto.builder()
                .id(marker.getId())
                .facilityId(marker.getFacility().getId())
                .machineId(marker.getMachine().getId())
                .locationX(marker.getLocationX())
                .locationY(marker.getLocationY())
                .build();
    }

    @Builder
    public MarkerDto(Long id, Long facilityId, Long machineId, Double locationX, Double locationY) {
        this.id = id;
        this.facilityId = facilityId;
        this.machineId = machineId;
        this.locationX = locationX;
        this.locationY = locationY;
    }
}
