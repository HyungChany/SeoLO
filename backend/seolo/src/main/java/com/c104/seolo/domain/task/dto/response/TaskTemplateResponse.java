package com.c104.seolo.domain.task.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@ToString
public class TaskTemplateResponse {
    private Map<String, List<Object[]>> templates;
}
