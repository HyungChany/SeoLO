package com.c104.seolo.domain.task.entity;

import com.c104.seolo.domain.machine.entity.Machine;
import com.c104.seolo.domain.task.enums.TaskStatus;
import com.c104.seolo.domain.user.entity.AppUser;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@Entity
@Table(name = "task_history")
public class TaskHistory {
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "task_id", nullable = false)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_template_id")
    private TaskTemplate taskTemplate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @Column(name = "task_start_datetime", nullable = false, updatable = false)
    private LocalDateTime taskStartDateTime;

    @Column(name = "task_end_datetime")
    private LocalDateTime taskEndDateTime;

    @Column(name = "task_end_estimated_datetime", nullable = false)
    private LocalDateTime taskEndEstimatedDateTime;

    @Column(name = "task_status", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Column(name = "task_precaution", length = 255)
    private String taskPrecaution;

    @Builder
    private TaskHistory(Long id, AppUser user, TaskTemplate taskTemplate, Machine machine, LocalDateTime taskStartDateTime, LocalDateTime taskEndDateTime, LocalDateTime taskEndEstimatedDateTime, TaskStatus taskStatus, String taskPrecaution){
        this.id = id;
        this.user = user;
        this.taskTemplate = taskTemplate;
        this.machine = machine;
        this.taskStartDateTime = taskStartDateTime;
        this.taskEndDateTime = taskEndDateTime;
        this.taskEndEstimatedDateTime = taskEndEstimatedDateTime;
        this.taskStatus = taskStatus;
        this.taskPrecaution = taskPrecaution;
    }

    public TaskHistory() {}
}
