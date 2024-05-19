package com.c104.seolo.domain.user.dto.request;

import com.c104.seolo.domain.user.exception.validation.PasswordConstraint;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserJoinRequest {

    @NotNull(message = "사번을 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "사번에는 특수문자를 포함할 수 없습니다.")
    private String username;

    @PasswordConstraint
    private String password;

    @NotNull(message = "회사코드를 입력해주세요")
    @Pattern(regexp = "^[A-Z]{3}\\d{3}[A-Z]{3}$", message = "회사코드 형식이 틀렸습니다 EX) ABC123KOR")
    private String companyCode;

    @Builder
    public UserJoinRequest(String username, String password, String companyCode) {
        this.username = username;
        this.password = password;
        this.companyCode = companyCode;
    }
}
