package com.c104.seolo.headquarter.employee.service.impl;

import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.headquarter.employee.dto.EmployeeDto;
import com.c104.seolo.headquarter.employee.dto.response.EmployeeResponse;
import com.c104.seolo.headquarter.employee.entity.Employee;
import com.c104.seolo.headquarter.employee.exception.EmployeeErrorCode;
import com.c104.seolo.headquarter.employee.repository.EmployeeRepository;
import com.c104.seolo.headquarter.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse getEmployee(String companyCode) {
        List<EmployeeDto> employeeDtoList = employeeRepository.findEmployeesByCompanyCode(companyCode);
        return EmployeeResponse.builder()
                .employees(employeeDtoList)
                .build();
    }

    @Override
    public EmployeeDto getEmployeeByEmployeeNum(String employeeNum) {
        Employee employeeOptional = employeeRepository.findEmployeeByEmployeeNum(employeeNum)
                .orElseThrow(() -> new CommonException(EmployeeErrorCode.NOT_EXIST_EMPLOYEE));
        return EmployeeDto.builder()
                .employeeNum(employeeOptional.getEmployeeNum())
                .employeeName(employeeOptional.getEmployeeName())
                .companyCode(employeeOptional.getCompanycode())
                .employeeTitle(employeeOptional.getEmployeeTitle())
                .employeeTeam(employeeOptional.getEmployeeTeam())
                .employeeBirthday(employeeOptional.getEmployeeBirthday())
                .employeeThum(employeeOptional.getEmployeeThum())
                .employeeJoinDate(employeeOptional.getEmployeeJoinDate())
                .employeeLeaveDate(employeeOptional.getEmployeeLeaveDate())
                .build();
    }
}
