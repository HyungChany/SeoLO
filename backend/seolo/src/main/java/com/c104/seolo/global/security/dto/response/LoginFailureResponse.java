package com.c104.seolo.global.security.dto.response;

import com.c104.seolo.global.exception.SeoloErrorResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginFailureResponse {
    private final SeoloErrorResponse seoloErrorResponse;
    private Integer failCount;

    @Builder
    public LoginFailureResponse(SeoloErrorResponse seoloErrorResponse, Integer failCount) {
        this.seoloErrorResponse = seoloErrorResponse;
        this.failCount = failCount;
    }
}
