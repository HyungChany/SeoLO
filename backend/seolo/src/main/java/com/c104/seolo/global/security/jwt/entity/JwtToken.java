package com.c104.seolo.global.security.jwt.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Getter
@RedisHash(value = "jwtToken", timeToLive = 36000)
public class JwtToken {
    @Id
    private String id;

    @Indexed
    private Long userId;

    @Indexed
    private String deviceType; // 기기 식별자 추가

    @Indexed
    private String accessToken;

    @Indexed
    private String refreshToken;

    @Builder
    public JwtToken(Long userId, String deviceType, String accessToken, String refreshToken) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.deviceType = deviceType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
