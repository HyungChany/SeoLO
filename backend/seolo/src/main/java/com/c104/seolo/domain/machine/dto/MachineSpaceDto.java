package com.c104.seolo.domain.machine.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
@Getter
@Setter
public class MachineSpaceDto {
    private Long id;
    private Float latitude;
    private Float longitude;

//    @JsonCreator
//    public MachineSpaceDto(@JsonProperty("id") Long id, @JsonProperty("latitude") Float latitude, @JsonProperty("longitude") Float longitude) {
//        this.id = id;
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }
}
