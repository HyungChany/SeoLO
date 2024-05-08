package com.c104.seolo.domain.task.repository;

import com.c104.seolo.domain.task.dto.info.TaskHistoryInfo;
import com.c104.seolo.domain.task.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Query("SELECT t FROM TaskHistory t WHERE t.machine.id = :machineId AND t.taskCode is NOT NULL ORDER BY t.taskStartDateTime DESC LIMIT 1")
    Optional<TaskHistory> getLatestTaskHistoryByMachineId(Long machineId);

    @Query("SELECT t FROM TaskHistory t WHERE (t.machine.id = :machineId OR t.user.id = :userId) AND t.taskCode is NOT NULL")
    Optional<TaskHistory> findByMachineIdAndUserIdOrTaskCode(Long machineId, Long userId);

    @Query("SELECT new com.c104.seolo.domain.task.dto.info.TaskHistoryInfo( " +
            "t.id, t.taskTemplate.taskType, t.taskStartDateTime, t.taskEndDateTime, t.taskEndEstimatedDateTime, " +
            "t.taskPrecaution, u.employee.company.companyCode, " +
            " m.facility.facilityName, m.name, m.number, " +
            "u.employee.employeeTeam, u.employee.employeeName, u.employee.employeeTitle " +
            ") FROM TaskHistory t join t.user u join t.machine m where t.user.employee.employeeNum = :employeeNum order by t.taskStartDateTime desc ")
    List<TaskHistoryInfo> getTaskHistoryByEmployeeNum(String employeeNum);
}
