package com.c104.seolo.domain.task.repository;

import com.c104.seolo.domain.task.dto.info.TaskHistoryInfo;
import com.c104.seolo.domain.task.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    @Query("SELECT new com.c104.seolo.domain.task.dto.info.TaskHistoryInfo( " +
            "t.id, tt.taskType, t.taskStartDateTime, t.taskEndDateTime, t.taskEndEstimatedDateTime, " +
            "t.taskPrecaution, u.employee.company.companyCode, " +
            " m.facility.facilityName, m.name, m.number, " +
            "u.employee.employeeTeam, u.employee.employeeName, u.employee.employeeTitle" +
            ") FROM TaskHistory t JOIN t.taskTemplate tt JOIN t.machine m JOIN t.user u " +
            "WHERE t.id = :taskId")
    TaskHistoryInfo getTaskHistoryInfoById(Long taskId);
}