package com.c104.seolo.headquarter.employee.repository;

import com.c104.seolo.headquarter.employee.dto.EmployeeDto;
import com.c104.seolo.headquarter.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findEmployeeByEmployeeNum(String employeeNum);

    @Query("SELECT new com.c104.seolo.headquarter.employee.dto.EmployeeDto(" +
            "e.employeeNum, e.employeeName, e.employeeTitle, e.employeeTeam, e.employeeBirthday, e.employeeThum, e.employeeJoinDate, e.employeeLeaveDate " +
            ") FROM Employee e " +
            "WHERE e.company.companyCode = :companyCode ")
    List<EmployeeDto> findEmployeesByCompanyCode(String companyCode);
}
