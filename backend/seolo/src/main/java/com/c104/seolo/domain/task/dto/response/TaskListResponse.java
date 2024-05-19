package com.c104.seolo.domain.task.dto.response;

import com.c104.seolo.domain.task.dto.info.TaskHistoryInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class TaskListResponse {
    private List<TaskHistoryInfo> tasks;
}
