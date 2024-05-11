package com.c104.seolo.domain.task.entity;

import com.c104.seolo.domain.core.enums.CODE;
import com.c104.seolo.domain.machine.entity.Machine;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.common.BaseEntity;
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
public class TaskHistory extends BaseEntity {
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

    @Column(name = "task_start_datetime")
    private LocalDateTime taskStartDateTime;

    @Column(name = "task_end_datetime")
    private LocalDateTime taskEndDateTime;

    @Column(name = "task_end_estimated_datetime")
    private LocalDateTime taskEndEstimatedDateTime;

    @Column(name = "task_code", length = 15)
    @Enumerated(EnumType.STRING)
    // 토큰을 발급한 상태(잠금준비) -> ISSUED
    // 실제로 자물쇠를 잠군 상태 -> LOCKED
    // 열려있는 기본 상태 -> NULL
    private CODE taskCode;

    @Column(name = "task_precaution", length = 255)
    private String taskPrecaution;

    @Builder
    public TaskHistory(Long id, AppUser user, TaskTemplate taskTemplate, Machine machine, LocalDateTime taskStartDateTime, LocalDateTime taskEndDateTime, LocalDateTime taskEndEstimatedDateTime, CODE taskCode, String taskPrecaution) {
        this.id = id;
        this.user = user;
        this.taskTemplate = taskTemplate;
        this.machine = machine;
        this.taskStartDateTime = taskStartDateTime;
        this.taskEndDateTime = taskEndDateTime;
        this.taskEndEstimatedDateTime = taskEndEstimatedDateTime;
        this.taskCode = taskCode;
        this.taskPrecaution = taskPrecaution;
    }

    public TaskHistory() {}
}
