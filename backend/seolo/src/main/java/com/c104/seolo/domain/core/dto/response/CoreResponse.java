package com.c104.seolo.domain.core.dto.response;

import com.c104.seolo.domain.core.entity.Token;
import com.c104.seolo.domain.task.dto.TaskHistoryDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CoreResponse {
    private final String nextCode;
    private final Token coreToken;
    private final TaskHistoryDto taskHistory;

    @Builder
    public CoreResponse(String nextCode, Token coreToken, TaskHistoryDto taskHistory) {
        this.nextCode = nextCode;
        this.coreToken = coreToken;
        this.taskHistory = taskHistory;
    }
}
