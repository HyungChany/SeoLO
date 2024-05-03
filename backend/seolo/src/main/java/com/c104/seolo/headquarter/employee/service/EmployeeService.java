package com.c104.seolo.headquarter.employee.service;

import com.c104.seolo.headquarter.employee.dto.EmployeeDto;
import com.c104.seolo.headquarter.employee.dto.response.EmployeeResponse;

public interface EmployeeService {
    EmployeeResponse getEmployee(String companyCode);
    EmployeeDto getEmployeeByEmployeeNum(String companyCode, String employeeNum);
}

