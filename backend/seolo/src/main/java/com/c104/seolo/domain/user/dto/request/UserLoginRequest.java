package com.c104.seolo.domain.user.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserLoginRequest {
    @NotNull(message = "사번을 입력해주세요")
    private String username;
    @NotNull(message = "비밀번호를 입력해주세요")
    private String password;
    @NotNull(message = "회사코드를 입력해주세요")
    private String companyCode;
}
