package com.c104.seolo.domain.core.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LockerEnrollRequest {

    @NotNull(message = "자물쇠 기기 고유번호를 입력해주세요.")
    private String uid;
}
