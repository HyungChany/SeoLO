package com.c104.seolo.domain.core.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class LockerRequest {
    private Integer battery;

    @JsonCreator
    public LockerRequest(@JsonProperty("battery") Integer battery) {
        this.battery = battery;
    }
}
