package com.c104.seolo.domain.user.repository;

import com.c104.seolo.domain.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmployee_EmployeeNum(String employeeNum);
}
