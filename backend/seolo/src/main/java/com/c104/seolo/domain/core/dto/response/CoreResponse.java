package com.c104.seolo.domain.core.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CoreResponse {
    private String nextCode;
    private String coreToken;

    @Builder
    public CoreResponse(String nextCode, String coreToken) {
        this.nextCode = nextCode;
        this.coreToken = coreToken;
    }
}
