package com.c104.seolo.domain.task.controller;

import com.c104.seolo.domain.task.dto.response.TaskHistoryResponse;
import com.c104.seolo.domain.task.service.TaskHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskHistoryService taskService;

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskHistoryResponse> getTask(
            @RequestHeader("Company-Code") String companyCode,
            @PathVariable("taskId") Long taskId
    ) {
        return ResponseEntity.ok(taskService.getTaskHistory(taskId, companyCode));
    }
}
