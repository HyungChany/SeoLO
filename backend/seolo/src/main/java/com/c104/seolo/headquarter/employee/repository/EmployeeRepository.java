package com.c104.seolo.headquarter.employee.repository;

import com.c104.seolo.headquarter.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findEmployeeByEmployeeNum(String employeeNum);
}
