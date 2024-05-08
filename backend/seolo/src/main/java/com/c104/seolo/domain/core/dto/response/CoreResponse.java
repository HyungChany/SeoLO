package com.c104.seolo.domain.core.dto.response;

import com.c104.seolo.domain.core.dto.TokenDto;
import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CoreResponse {
    private final CODE nextCode;
    private final TokenDto coreToken;
    private final TaskHistoryDto taskHistory;
    private final HttpStatus httpStatus;
    private final String message;

    @Builder
    public CoreResponse(CODE nextCode, TokenDto coreToken, TaskHistoryDto taskHistory, HttpStatus httpStatus, String message) {
        this.nextCode = nextCode;
        this.coreToken = coreToken;
        this.taskHistory = taskHistory;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
