package com.c104.seolo.global.security.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PINLoginResponse {

    private boolean isAuthenticated;
    private Integer failCount;

    @Builder
    public PINLoginResponse(boolean isAuthenticated, Integer failCount) {
        this.isAuthenticated = isAuthenticated;
        this.failCount = failCount;
    }
}
