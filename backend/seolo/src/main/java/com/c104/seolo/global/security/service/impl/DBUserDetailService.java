package com.c104.seolo.global.security.service.impl;

import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmployee_EmployeeNum(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with employee number: " + username));
    }
}
