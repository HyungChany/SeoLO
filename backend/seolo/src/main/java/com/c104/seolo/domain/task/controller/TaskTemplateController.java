package com.c104.seolo.domain.task.controller;

import com.c104.seolo.domain.task.dto.response.TaskTemplateResponse;
import com.c104.seolo.domain.task.service.TaskTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task-templates")
@RequiredArgsConstructor
@Slf4j
public class TaskTemplateController {
    private final TaskTemplateService taskTemplateService;

    @GetMapping
    public ResponseEntity<TaskTemplateResponse> getTaskTemplates() {
        TaskTemplateResponse response = taskTemplateService.getTemplates();
        return ResponseEntity.ok(response);
    }
}
