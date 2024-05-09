package com.c104.seolo.domain.report.dto;

import com.c104.seolo.domain.report.entity.Report;
import com.c104.seolo.domain.task.enums.TASKTYPE;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NewReport {
    private String machineNumber;
    private String machineName;
    private String workerNumber;
    private String workerName;
    private TASKTYPE taskType;
    private boolean isAccident;
    private String accidentType;
    private Integer victimsNum;
    private LocalDateTime taskStartDateTime;
    private LocalDateTime taskEndDateTime;
    private boolean needRecheck;

    @Builder
    public NewReport(String machineNumber, String machineName, String workerNumber, String workerName, TASKTYPE taskType, boolean isAccident, String accidentType, Integer victimsNum, LocalDateTime taskStartDateTime, LocalDateTime taskEndDateTime, boolean needRecheck) {
        this.machineNumber = machineNumber;
        this.machineName = machineName;
        this.workerNumber = workerNumber;
        this.workerName = workerName;
        this.taskType = taskType;
        this.isAccident = isAccident;
        this.accidentType = accidentType;
        this.victimsNum = victimsNum;
        this.taskStartDateTime = taskStartDateTime;
        this.taskEndDateTime = taskEndDateTime;
        this.needRecheck = needRecheck;
    }

    public Report toEntity() {
        return Report.builder()
                .machineNumber(machineNumber)
                .machineName(machineName)
                .workerNumber(workerNumber)
                .workerName(workerName)
                .taskType(taskType)
                .isAccident(isAccident)
                .accidentType(accidentType)
                .victimsNum(victimsNum)
                .taskStartDateTime(taskStartDateTime)
                .taskEndDateTime(taskEndDateTime)
                .build();
    }

    public static NewReport of(String machineNumber, String machineName, String workerNumber ,String workerName, TASKTYPE taskType, boolean isAccident, String accidentType, Integer victimsNum, LocalDateTime taskStartDateTime, LocalDateTime taskEndDateTime) {
        return NewReport.builder()
                .machineNumber(machineNumber)
                .machineName(machineName)
                .workerNumber(workerNumber)
                .workerName(workerName)
                .taskType(taskType)
                .isAccident(isAccident)
                .accidentType(accidentType)
                .victimsNum(victimsNum)
                .taskStartDateTime(taskStartDateTime)
                .taskEndDateTime(taskEndDateTime)
                .build();
    }
}
