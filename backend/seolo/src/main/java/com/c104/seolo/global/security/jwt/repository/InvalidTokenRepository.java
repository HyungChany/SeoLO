package com.c104.seolo.global.security.jwt.repository;

import com.c104.seolo.global.security.jwt.entity.InvalidToken;
import org.springframework.data.repository.CrudRepository;

public interface InvalidTokenRepository extends CrudRepository<InvalidToken, String> {
}
