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
    private final String tokenValue;
    private final TaskHistoryDto taskHistory;
    private final CheckMoreResponse checkMoreResponse;
    private final HttpStatus httpStatus;
    private final String message;

    @Builder
    public CoreResponse(CODE nextCode, String tokenValue, TaskHistoryDto taskHistory, CheckMoreResponse checkMoreResponse, HttpStatus httpStatus, String message) {
        this.nextCode = nextCode;
        this.tokenValue = tokenValue;
        this.taskHistory = taskHistory;
        this.checkMoreResponse = checkMoreResponse;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
