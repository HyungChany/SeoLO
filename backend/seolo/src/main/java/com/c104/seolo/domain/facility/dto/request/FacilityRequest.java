package com.c104.seolo.domain.facility.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class FacilityRequest {
    private Long id;
    private String name;
    private String address;
    private String layout;
    private String thumbnail;

    @JsonCreator
    public FacilityRequest(Long id, String name, String address, String layout, String thumbnail) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.layout = layout;
        this.thumbnail = thumbnail;
    }
}
