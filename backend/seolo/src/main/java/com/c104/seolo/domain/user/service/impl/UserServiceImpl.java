package com.c104.seolo.domain.user.service.impl;

import com.c104.seolo.domain.user.dto.request.UserJoinRequest;
import com.c104.seolo.domain.user.dto.response.UserInfoResponse;
import com.c104.seolo.domain.user.dto.response.UserJoinResponse;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.domain.user.enums.ROLES;
import com.c104.seolo.domain.user.exception.UserErrorCode;
import com.c104.seolo.domain.user.repository.UserRepository;
import com.c104.seolo.domain.user.service.UserService;
import com.c104.seolo.global.exception.AuthException;
import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.global.security.exception.AuthErrorCode;
import com.c104.seolo.headquarter.employee.entity.Employee;
import com.c104.seolo.headquarter.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserJoinResponse registUser(UserJoinRequest userJoinRequest) {
        // 등록하려는 유저정보가 이미 DB에 있으면 예외처리
        userRepository.findAppUserByEmployeeNum(userJoinRequest.getUsername()).ifPresent(u  -> {
            throw new CommonException(UserErrorCode.APPUSER_ALREADY_IN_DB);
        });

        // 1. 유저 사번으로 Employee DB에서 조회
        Employee employee = employeeRepository.findEmployeeByEmployeeNum(userJoinRequest.getUsername())
                .orElseThrow(() -> new AuthException(AuthErrorCode.NOT_EXIST_EMPLOYEE));// 2. 해당 유저와 사번 비교

        // 3. AppUser로 등록
        AppUser newUser = createAppUser(employee, userJoinRequest.getPassword());
        userRepository.save(newUser);

        return UserJoinResponse.builder()
                .username(newUser.getUsername())
                .companyCode(userJoinRequest.getCompanyCode())
                .build();
    }

    @Override
    public AppUser createAppUser(Employee employee, String password) {
        return AppUser.builder()
                .employee(employee)
                .role(ROLES.ROLE_WORKER)
                .password(passwordEncoder.encode(password))
                .build();
    }


    @Override
    public UserInfoResponse getUserInfo(AppUser appUser) {
        UserInfoResponse res = UserInfoResponse.builder()
                .id(appUser.getId())
                .employee(appUser.getEmployee())
                .ROLES(appUser.getROLES())
                .statusCODE(appUser.getStatusCODE())
                .PIN(appUser.getPIN())
                .isLocked(appUser.isLocked())
                .build();
        return res;
    }
}
