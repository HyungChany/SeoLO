package com.c104.seolo.global.security.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthSuccessResponse {
    private String username;
    private String companyCode;
    private String JSESSIONID;


    @Builder
    public AuthSuccessResponse(String username, String companyCode, String JSESSIONID) {
        this.username = username;
        this.companyCode = companyCode;
        this.JSESSIONID = JSESSIONID;
    }
}
