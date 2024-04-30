package com.c104.seolo.global.security.service;

import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.domain.user.exception.UserErrorCode;
import com.c104.seolo.domain.user.repository.UserRepository;
import com.c104.seolo.global.exception.AuthException;
import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.global.security.exception.AuthErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class DBUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public DBUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // UserRepository 조회 로직필요
    @Override
    public AppUser loadUserByUsername(String username) throws AuthException {
        return userRepository.findAppUserByEmployeeNum(username)
                .orElseThrow(() -> new AuthException(AuthErrorCode.NOT_EXIST_APPUSER));
    }
}
