package com.c104.seolo.domain.user.dto.request;

import com.c104.seolo.domain.user.exception.validation.PasswordConstraint;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserPwdResetRequest {


    @PasswordConstraint
    private String newPassword;

    private String checkNewPassword;
}
