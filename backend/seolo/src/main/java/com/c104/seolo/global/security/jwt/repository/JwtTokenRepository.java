package com.c104.seolo.global.security.jwt.repository;

import com.c104.seolo.global.security.jwt.entity.JwtToken;
import org.springframework.data.repository.CrudRepository;

public interface JwtTokenRepository extends CrudRepository<JwtToken, Long> {
}
