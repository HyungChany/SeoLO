package com.c104.seolo.global.security.jwt.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@RedisHash(value = "blackList", timeToLive = 900)
public class InvalidJwtToken {
    @Id
    private String accessToken;

    @Builder
    public InvalidJwtToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
