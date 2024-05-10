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

    @Column(name = "facility_name", length = 20, nullable = false)
    private String facilityName;

    @Column(name = "machine_number", length = 30, nullable = false)
    private String machineNumber;

    @Column(name = "machine_name", length = 30, nullable = false)
    private String machineName;

    @Column(name = "worker_number", length = 30, nullable = false)
    private String workerNumber;

    @Column(name = "worker_name", length = 30, nullable = false)
    private String workerName;

    @Column(name = "worker_team", length = 30, nullable = false)
    private String workerTeam;

    @Column(name = "worker_title", length = 30, nullable = false)
    private String workerTitle;

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

    public void updateReportDetails(boolean isAccident, String accidentType, Integer victimsNum) {
        this.isAccident = isAccident;
        this.accidentType = accidentType;
        this.victimsNum = victimsNum;
    }

    protected Report() {}

    private Report(Builder builder) {
        this.facilityName = builder.facilityName;
        this.machineNumber = builder.machineNumber;
        this.machineName = builder.machineName;
        this.workerNumber = builder.workerNumber;
        this.workerName = builder.workerName;
        this.workerTeam = builder.workerTeam;
        this.workerTitle = builder.workerTitle;
        this.tasktype = builder.taskType;
        this.isAccident = builder.isAccident;
        this.accidentType = builder.accidentType;
        this.victimsNum = builder.victimsNum;
        this.taskStartDateTime = builder.taskStartDateTime;
        this.taskEndDateTime = builder.taskEndDateTime;
    }

    public static class Builder {
        private String facilityName;
        private String machineNumber;
        private String machineName;
        private String workerNumber;
        private String workerName;
        private String workerTeam;
        private String workerTitle;
        private TASKTYPE taskType;
        private boolean isAccident = false;
        private String accidentType;
        private Integer victimsNum = 0;
        private LocalDateTime taskStartDateTime;
        private LocalDateTime taskEndDateTime;

        public Builder facilityName(String newFacilityName) {
            if (newFacilityName == null) {
                throw new IllegalArgumentException("facilityName cannot be null");
            }
            this.facilityName = newFacilityName;
            return this;
        }

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

        public Builder workerNumber(String newWorkerNumber) {
            if (newWorkerNumber == null) {
                throw new IllegalArgumentException("workerNumber cannot be null");
            }
            this.workerNumber = newWorkerNumber;
            return this;
        }

        public Builder workerTeam(String newWorkerTeam) {
            if (newWorkerTeam == null) {
                throw new IllegalArgumentException("workerTeam cannot be null");
            }
            this.workerTeam = newWorkerTeam;
            return this;
        }

        public Builder workerTitle(String newWorkerTitle) {
            if (newWorkerTitle == null) {
                throw new IllegalArgumentException("workerTitle cannot be null");
            }
            this.workerTitle = newWorkerTitle;
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

        public Report build() {
            if (facilityName == null) {
                throw new IllegalStateException("Cannot build Report object: facilityName is null");
            }
            if (machineNumber == null) {
                throw new IllegalStateException("Cannot build Report object: machineNumber is null");
            }
            if (workerTeam == null) {
                throw new IllegalStateException("Cannot build Report object: workerTeam is null");
            }
            if (workerTitle == null) {
                throw new IllegalStateException("Cannot build Report object: workerTitle is null");
            }
            if (machineName == null) {
                throw new IllegalStateException("Cannot build Report object: machineName is null");
            }
            if (workerNumber == null) {
                throw new IllegalStateException("Cannot build Report object: workerNumber is null");
            }
            if (workerName == null) {
                throw new IllegalStateException("Cannot build Report object: workerName is null");
            }
            if (taskType == null) {
                throw new IllegalStateException("Cannot build Report object: taskType is null");
            }
            return new Report(this);
        }

    }
    public static Builder builder() {
        return new Builder();
    }
}
