package com.c104.seolo.domain.user.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserLoginResponse {
    private String username;
    private String companyCode;
    private String accessToken;

    @Builder
    public UserLoginResponse(String username, String companyCode, String accessToken) {
        this.username = username;
        this.companyCode = companyCode;
        this.accessToken = accessToken;
    }
}
