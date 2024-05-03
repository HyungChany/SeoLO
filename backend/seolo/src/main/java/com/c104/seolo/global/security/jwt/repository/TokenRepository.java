package com.c104.seolo.global.security.jwt.repository;

import com.c104.seolo.global.security.jwt.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
}
