package com.c104.seolo.domain.report.dto;

import com.c104.seolo.domain.report.entity.Report;
import com.c104.seolo.domain.task.enums.TASKTYPE;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReportDto {
    private Long reportId;
    private String machineNumber;
    private String machineName;
    private String workerNumber;
    private String workerName;
    private TASKTYPE tasktype;
    private boolean isAccident;
    private String accidentType;
    private Integer victimsNum;
    private LocalDateTime taskStartDateTime;
    private LocalDateTime taskEndDateTime;

    @Builder
    public ReportDto(Long reportId, String machineNumber, String machineName, String workerNumber, String workerName, TASKTYPE tasktype, boolean isAccident, String accidentType, Integer victimsNum, LocalDateTime taskStartDateTime, LocalDateTime taskEndDateTime) {
        this.reportId = reportId;
        this.machineNumber = machineNumber;
        this.machineName = machineName;
        this.workerNumber = workerNumber;
        this.workerName = workerName;
        this.tasktype = tasktype;
        this.isAccident = isAccident;
        this.accidentType = accidentType;
        this.victimsNum = victimsNum;
        this.taskStartDateTime = taskStartDateTime;
        this.taskEndDateTime = taskEndDateTime;
    }

    public Report toEntity() {
        return Report.builder()
                .machineNumber(machineNumber)
                .machineName(machineName)
                .workerNumber(workerNumber)
                .workerName(workerName)
                .taskType(tasktype)
                .isAccident(isAccident)
                .accidentType(accidentType)
                .victimsNum(victimsNum)
                .taskStartDateTime(taskStartDateTime)
                .taskEndDateTime(taskEndDateTime)
                .build();
    }

    public static ReportDto of(Report report) {
        return ReportDto.builder()
                .reportId(report.getId())
                .machineNumber(report.getMachineNumber())
                .machineName(report.getMachineName())
                .workerNumber(report.getWorkerNumber())
                .workerName(report.getWorkerName())
                .tasktype(report.getTasktype())
                .isAccident(report.isAccident())
                .accidentType(report.getAccidentType())
                .victimsNum(report.getVictimsNum())
                .taskStartDateTime(report.getTaskStartDateTime())
                .taskEndDateTime(report.getTaskEndDateTime())
                .build();
    }
}
