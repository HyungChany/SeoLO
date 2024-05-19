package com.c104.seolo.domain.user.dto.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserJoinResponse {
    private String username;
    private String companyCode;

    @Builder
    public UserJoinResponse(String username, String companyCode) {
        this.username = username;
        this.companyCode = companyCode;
    }
}
