package com.c104.seolo.domain.user.service.impl;

import com.c104.seolo.domain.user.dto.info.UserListInfo;
import com.c104.seolo.domain.user.dto.request.UserJoinRequest;
import com.c104.seolo.domain.user.dto.request.UserPwdCheckRequest;
import com.c104.seolo.domain.user.dto.request.UserPwdResetRequest;
import com.c104.seolo.domain.user.dto.response.UserInfoResponse;
import com.c104.seolo.domain.user.dto.response.UserJoinResponse;
import com.c104.seolo.domain.user.dto.response.UserListResponse;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.domain.user.enums.ROLES;
import com.c104.seolo.domain.user.exception.UserErrorCode;
import com.c104.seolo.domain.user.repository.UserRepository;
import com.c104.seolo.domain.user.service.UserService;
import com.c104.seolo.global.exception.AuthException;
import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.global.security.exception.AuthErrorCode;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import com.c104.seolo.global.security.service.DBUserDetailService;
import com.c104.seolo.headquarter.employee.entity.Employee;
import com.c104.seolo.headquarter.employee.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final DBUserDetailService dbUserDetailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, DBUserDetailService dbUserDetailService) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.dbUserDetailService = dbUserDetailService;
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
                .PIN(passwordEncoder.encode(employee.getBirthdayMonthDay())) // PIN 기본값으로 생년월일 암호화 저장
                .build();
    }


    @Override
    public UserInfoResponse getUserInfo(CCodePrincipal cCodePrincipal) {
        AppUser appUser = dbUserDetailService.loadUserById(cCodePrincipal.getId());

        return UserInfoResponse.builder()
                .id(appUser.getId())
                .employee(appUser.getEmployee())
                .ROLES(appUser.getROLES())
                .statusCODE(appUser.getStatusCODE())
                .PIN(appUser.getPIN())
                .isLocked(appUser.isLocked())
                .build();
    }

    @Override
    public void checkSamePassword(CCodePrincipal cCodePrincipal, UserPwdCheckRequest userPwdCheckRequest) {
        AppUser appUser = dbUserDetailService.loadUserById(cCodePrincipal.getId());

        if (!passwordEncoder.matches(userPwdCheckRequest.getNowPassword(),appUser.getPassword())) {
            throw new AuthException(AuthErrorCode.INVALID_PASSWORD_FOR_CHECK);
        }
    }

    @Transactional
    @Override
    public void resetUserPassword(CCodePrincipal cCodePrincipal ,UserPwdResetRequest userPwdResetRequest) {
        String newPassword = userPwdResetRequest.getNewPassword();

        // 1. 유저ID를 받아서 해당 튜플을 찾는다.
        AppUser user = dbUserDetailService.loadUserById(cCodePrincipal.getId());

        // 2. 새로 설정하려는 비밀번호와 체크용 비밀번호 확인 값이 일치하는지 비교한다.
        if (!newPassword.equals(userPwdResetRequest.getCheckNewPassword())) {
            throw new AuthException(AuthErrorCode.NOT_SAME_AS_CHECKPWD);
        }

        // 3. 일치하면 기존 비밀번호 데이터를 새로 설정하는 비밀번호로 바꾸고 로그아웃시킨다.
        user.changePassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public UserListResponse getUserList(String companyCode) {
        List<AppUser> appUsers = userRepository.findAppUserByCompanyCode(companyCode);

        List<UserListInfo> userListInfos = new ArrayList<>();
        appUsers.forEach(userinfo -> userListInfos.add(UserListInfo.ofButTranslateRoleToKorean(userinfo)));

        return UserListResponse.builder()
                .workers(userListInfos)
                .build();
    }
}
