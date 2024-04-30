package com.c104.seolo.domain.user.service;

import com.c104.seolo.domain.user.dto.request.UserJoinRequest;
import com.c104.seolo.domain.user.dto.response.UserInfoResponse;
import com.c104.seolo.domain.user.dto.response.UserJoinResponse;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.headquarter.employee.entity.Employee;

public interface UserService {

    UserJoinResponse registUser(UserJoinRequest userJoinRequest);
    AppUser createAppUser(Employee employee, String password);
    UserInfoResponse getUserInfo(AppUser appUser);
}
