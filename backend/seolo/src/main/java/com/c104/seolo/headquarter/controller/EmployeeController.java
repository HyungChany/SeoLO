package com.c104.seolo.headquarter.controller;

import com.c104.seolo.headquarter.employee.dto.EmployeeDto;
import com.c104.seolo.headquarter.employee.dto.response.EmployeeResponse;
import com.c104.seolo.headquarter.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;

    @Secured("ROLE_MANAGER")
    @GetMapping
    public ResponseEntity<EmployeeResponse> getEmployees(
            @RequestHeader("Company-Code") String companyCode
    ) {
        return ResponseEntity.ok(employeeService.getEmployee(companyCode));
    }

    @Secured("ROLE_MANAGER")
    @GetMapping("/{employeeNum}")
    public ResponseEntity<EmployeeDto> getEmployee(
            @RequestHeader("Company-Code") String companyCode,
            @PathVariable String employeeNum
    ) {
        return ResponseEntity.ok(employeeService.getEmployeeByEmployeeNum(companyCode, employeeNum));
    }
}

