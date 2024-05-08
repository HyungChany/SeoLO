package com.c104.seolo.domain.report.entity;

import com.c104.seolo.domain.task.enums.TASKTYPE;
import com.c104.seolo.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Entity
@Table(name = "loto_report")
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loto_report_id", nullable = false)
    private Long id;

    @Column(name = "machine_number", length = 30, nullable = false)
    private String machineNumber;

    @Column(name = "machine_name", length = 30, nullable = false)
    private String machineName;

    @Column(name = "worker_name", length = 30, nullable = false)
    private String workerName;

    @Column(name = "task_type", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private TASKTYPE tasktype;

    @Column(name = "isAccident")
    private boolean isAccident;

    @Column(name = "accident_type", length = 150)
    private String accidentType;

    @Column(name = "victims_num")
    private Integer victimsNum;

    @Column(name = "start_datetime")
    private LocalDateTime taskStartDateTime;

    @Column(name = "end_datetime")
    private LocalDateTime taskEndDateTime;

    @Column(name = "need_recheck")
    private boolean needRecheck;

    protected Report() {}

    private Report(Builder builder) {
        this.machineNumber = builder.machineNumber;
        this.machineName = builder.machineName;
        this.workerName = builder.workerName;
        this.tasktype = builder.taskType;
        this.isAccident = builder.isAccident;
        this.accidentType = builder.accidentType;
        this.victimsNum = builder.victimsNum;
        this.taskStartDateTime = builder.taskStartDateTime;
        this.taskEndDateTime = builder.taskEndDateTime;
        this.needRecheck = builder.needRecheck;
    }

    public static class Builder {
        private String machineNumber;
        private String machineName;
        private String workerName;
        private TASKTYPE taskType;
        private boolean isAccident = false;
        private String accidentType;
        private Integer victimsNum = 0;
        private LocalDateTime taskStartDateTime;
        private LocalDateTime taskEndDateTime;
        private boolean needRecheck = false;

        public Builder machineNumber(String newMachineNumber) {
            if (newMachineNumber == null) {
                throw new IllegalArgumentException("machineNumber cannot be null");
            }
            this.machineNumber = newMachineNumber;
            return this;
        }

        public Builder machineName(String newMachineName) {
            if (newMachineName == null) {
                throw new IllegalArgumentException("machineName cannot be null");
            }
            this.machineName = newMachineName;
            return this;
        }

        public Builder workerName(String newWorkerName) {
            if (newWorkerName == null) {
                throw new IllegalArgumentException("workerName cannot be null");
            }
            this.workerName = newWorkerName;
            return this;
        }

        public Builder taskType(TASKTYPE tasktype) {
            if (tasktype == null) {
                throw new IllegalArgumentException("tasktype cannot be null");
            }
            this.taskType = tasktype;
            return this;
        }

        public Builder isAccident(boolean isAccident) {
            this.isAccident = isAccident;
            return this;
        }

        public Builder accidentType(String newAccidentType) {
            this.accidentType = newAccidentType;
            return this;
        }

        public Builder taskStartDateTime(LocalDateTime taskStartDateTime) {
            this.taskStartDateTime = taskStartDateTime;
            return this;
        }

        public Builder taskEndDateTime(LocalDateTime taskEndDateTime) {
            this.taskEndDateTime = taskEndDateTime;
            return this;
        }

        public Builder victimsNum(Integer newVictimsNum) {
            this.victimsNum = newVictimsNum;
            return this;
        }

        public Builder needRecheck(boolean needRecheck) {
            this.needRecheck = needRecheck;
            return this;
        }

        public Report build() {
            if (machineNumber == null || machineName == null || workerName == null || taskType == null) {
                throw new IllegalStateException("Cannot build Report object, one or more required fields are not set");
            }
            return new Report(this);
        }
    }
    public static Builder builder() {
        return new Builder();
    }
}
