package com.c104.seolo.domain.user.repository;

import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.domain.user.enums.ROLES;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE u.employee.employeeNum = :employeeNum")
    Optional<AppUser> findAppUserByEmployeeNum(@Param("employeeNum") String employeeNum);

    @Query("SELECT u.ROLES FROM AppUser u WHERE u.employee.employeeNum = :employeeNum AND u.employee.company.companyCode = :companyCode")
    ROLES findRoleByEmployeeNumAndCompanyCode(@Param("employeeNum") String employeeNum, @Param("companyCode") String companyCode);
}
