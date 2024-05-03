package com.c104.seolo.domain.machine.dto.request;

import com.c104.seolo.domain.machine.dto.MachineSpaceDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
@Getter
@Setter
public class MachineSpaceRequest {
    List<MachineSpaceDto> spaces;
}
