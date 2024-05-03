package com.c104.seolo.domain.task.entity;

import com.c104.seolo.domain.task.enums.TaskType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@Entity
@Table(name = "task_template")
public class TaskTemplate {
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "task_template_id", nullable = false)
    @Id
    private Long id;

    @Column(name = "task_template_type", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @Column(name = "task_template_precaution")
    private String precaution;

    @Builder
    private TaskTemplate(Long id, TaskType taskType, String precaution) {
        this.id = id;
        this.taskType = taskType;
        this.precaution = precaution;
    }

    public TaskTemplate() {}
}
