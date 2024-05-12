package com.c104.seolo.domain.user.repository;

import com.c104.seolo.domain.task.entity.TaskHistory;
import com.c104.seolo.domain.user.dto.info.UserListInfo;
import com.c104.seolo.domain.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE u.employee.employeeNum = :employeeNum")
    Optional<AppUser> findAppUserByEmployeeNum(@Param("employeeNum") String employeeNum);

    @Query("SELECT u FROM AppUser u JOIN u.employee e where e.company.companyCode = :companyCode")
    List<AppUser> findAppUserByCompanyCode(@Param("companyCode") String companyCode);

    @Query("SELECT new com.c104.seolo.domain.user.dto.info.UserListInfo ( " +
            "u.id, e.employeeName, e.employeeTitle, e.employeeTeam, e.employeeThum " +
            ") from AppUser u JOIN u.employee e where e.company.companyCode = :companyCode")
    List<UserListInfo> findAppUserListByCompanyCode(@Param("companyCode") String companyCode);
}
