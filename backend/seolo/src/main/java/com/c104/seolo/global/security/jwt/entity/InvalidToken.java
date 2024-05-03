package com.c104.seolo.global.security.jwt.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@RedisHash(value = "blackList", timeToLive = 1 * 60 * 60 * 1000)
public class InvalidToken {
    @Id
    private String accessToken;

    @Builder
    public InvalidToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
