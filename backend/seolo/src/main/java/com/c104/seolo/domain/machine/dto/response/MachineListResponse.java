package com.c104.seolo.domain.machine.dto.response;

import com.c104.seolo.domain.machine.dto.MachineListDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
@Getter
public class MachineListResponse {
    Optional<List<MachineListDto>> machines;
}
