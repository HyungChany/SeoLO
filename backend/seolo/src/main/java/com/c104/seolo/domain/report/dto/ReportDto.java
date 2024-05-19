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
    private String facilityName;
    private String machineNumber;
    private String machineName;
    private String workerNumber;
    private String workerName;
    private String workerTeam;
    private String workerTitle;
    private TASKTYPE tasktype;
    private boolean isAccident;
    private String accidentType;
    private Integer victimsNum;
    private LocalDateTime taskStartDateTime;
    private LocalDateTime taskEndDateTime;

    @Builder
    public ReportDto(Long reportId, String facilityName, String machineNumber, String machineName, String workerNumber, String workerName, String workerTeam, String workerTitle, TASKTYPE tasktype, boolean isAccident, String accidentType, Integer victimsNum, LocalDateTime taskStartDateTime, LocalDateTime taskEndDateTime) {
        this.reportId = reportId;
        this.facilityName = facilityName;
        this.machineNumber = machineNumber;
        this.machineName = machineName;
        this.workerNumber = workerNumber;
        this.workerName = workerName;
        this.workerTeam = workerTeam;
        this.workerTitle = workerTitle;
        this.tasktype = tasktype;
        this.isAccident = isAccident;
        this.accidentType = accidentType;
        this.victimsNum = victimsNum;
        this.taskStartDateTime = taskStartDateTime;
        this.taskEndDateTime = taskEndDateTime;
    }

    public static ReportDto of(Report report) {
        return ReportDto.builder()
                .reportId(report.getId())
                .facilityName(report.getFacilityName())
                .machineNumber(report.getMachineNumber())
                .machineName(report.getMachineName())
                .workerNumber(report.getWorkerNumber())
                .workerName(report.getWorkerName())
                .workerTeam(report.getWorkerTeam())
                .workerTitle(report.getWorkerTitle())
                .tasktype(report.getTasktype())
                .isAccident(report.isAccident())
                .accidentType(report.getAccidentType())
                .victimsNum(report.getVictimsNum())
                .taskStartDateTime(report.getTaskStartDateTime())
                .taskEndDateTime(report.getTaskEndDateTime())
                .build();
    }
}
