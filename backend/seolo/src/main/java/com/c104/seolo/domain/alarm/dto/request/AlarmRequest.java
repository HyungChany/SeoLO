package com.c104.seolo.domain.alarm.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AlarmRequest {
    private Long receiveUserId;
    private String alarmType;
    private String alarmContent;
    private String alarmRedirect;
}
