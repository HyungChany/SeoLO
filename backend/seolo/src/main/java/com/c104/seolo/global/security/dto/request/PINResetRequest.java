package com.c104.seolo.global.security.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PINResetRequest {

    @NotBlank(message = "PIN은 빈값일 수 없습니다.")
    @Size(min = 4, max = 4, message = "PIN은 반드시 4자리여야 합니다.")
    private String newPin;
}
