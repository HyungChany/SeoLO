package com.c104.seolo.domain.user.repository;

import com.c104.seolo.domain.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE u.employee.employeeNum = :employeeNum")
    Optional<AppUser> findAppUserByEmployeeNum(@Param("employeeNum") String employeeNum);
}
