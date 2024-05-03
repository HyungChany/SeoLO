package com.c104.seolo.global.security.jwt.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "jwtToken", timeToLive = 7 * 24 * 60 * 60 * 1000)
public class Token {
    @Id
    private Long id;

    @Indexed
    private String refreshToken;

    @Builder
    public Token(Long id, String refreshToken) {
        this.id = id;
        this.refreshToken = refreshToken;
    }
}
