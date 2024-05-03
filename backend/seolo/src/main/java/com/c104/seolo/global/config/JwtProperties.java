package com.c104.seolo.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.jwt") // spring.jwt.xxx 로 바인딩
public class JwtProperties {
    // JWT 설정을 외부에서 읽어오기 위한 클래스

    private String access;  // spring.jwt.access
    private String refresh;
    private Long accesstime;
    private Long refreshtime;
}
