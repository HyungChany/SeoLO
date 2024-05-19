package com.c104.seolo.domain.checklist.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class CheckListRequest {
    private String context;

    @JsonCreator
    public CheckListRequest(@JsonProperty("context") String context) {
        this.context = context;
    }
}
