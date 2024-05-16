package com.c104.seolo.global.security.jwt;

import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.config.JwtProperties;
import com.c104.seolo.global.exception.AuthException;
import com.c104.seolo.global.security.enums.DEVICETYPE;
import com.c104.seolo.global.security.exception.JwtErrorCode;
import com.c104.seolo.global.security.jwt.repository.InvalidTokenRepository;
import com.c104.seolo.global.security.jwt.repository.JwtTokenRepository;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtProperties jwtProperties;
    private final InvalidTokenRepository invalidTokenRepository;
    private final JwtTokenRepository jwtTokenRepository;
    private static final ZoneId zoneId = ZoneId.of("Asia/Seoul");
    private String accessSecretKey;
    private String refreshSecretKey;


    @PostConstruct
    protected void encodeKey() {
        accessSecretKey = Base64.getEncoder().encodeToString(jwtProperties.getAccess().getBytes());
        refreshSecretKey = Base64.getEncoder().encodeToString(jwtProperties.getRefresh().getBytes());
    }

    // 토큰 발행시간
    public Date getIssuedAt() {
        return Date.from(ZonedDateTime.now(zoneId).toInstant());
    }

    // 토큰만료시간
    public Date getExpiredTime(Long period) {
//        log.info("Token : lifetime = {}", period);
        return Date.from(ZonedDateTime.now(zoneId).plus(Duration.ofMillis(period)).toInstant());
    }

    public void validateDeviceType(String deviceType) {
        try {
            DEVICETYPE.valueOf(deviceType);
        } catch (IllegalArgumentException e) {
            log.error("Invalid Device-Type: {}", deviceType);
            throw new AuthException(JwtErrorCode.WRONG_DEVICE_TYPE);
        }
    }

    public String issueAccessToken(AppUser appUser, String deviceType) {
        validateDeviceType(deviceType);

        // JWT에는 유저 기본키, 사번, 회사코드, 권한을 저장한다.
        List<String> authorityList = appUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        String compact = Jwts.builder()
                .setSubject(appUser.getUsername())
                .claim("userId", appUser.getId())
                .claim("companyCode", appUser.getCompanyCode())
                .claim("authorities", authorityList)
                .claim("deviceType", deviceType)
                .setIssuedAt(getIssuedAt())
                .setExpiration(getExpiredTime(jwtProperties.getAccesstime()))
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();

        return compact;
    }

    public String issueRefreshToken(AppUser appUser) {
        // JWT에는 유저 기본키, 사번, 회사코드, 권한을 저장한다.
        List<String> authorityList = appUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(appUser.getUsername())
                .claim("userId", appUser.getId())
                .claim("companyCode", appUser.getCompanyCode())
                .claim("role", authorityList)
                .setIssuedAt(getIssuedAt())
                .setExpiration(getExpiredTime(jwtProperties.getRefreshtime()))
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)
                .compact();
    }

    public Jws<Claims> validateAccessToken(final String token, String deviceType) {
        try {
            log.info("토큰 검증 시작 : {}", token);
            if (token == null || token.isEmpty()) {
                log.error("Token is null or empty");
                throw new AuthException(JwtErrorCode.INVALID_TOKEN);
            }

            // Redis에 저장된 무효 토큰 검증
            invalidTokenRepository.findById(token).ifPresent(value -> {
                log.info("무효화 된 토큰 : {}", token);
                throw new AuthException(JwtErrorCode.TOKEN_SIGNATURE_ERROR);
            });

            // Redis에 저장된 유효 토큰 검증
            if (!jwtTokenRepository.existsByAccessToken(token)) {
                log.info("해당 accessToken 저장소에 없음 : {}", token);
                throw new AuthException(JwtErrorCode.INVALID_TOKEN);
            }

            // 토큰의 서명을 검증하고 클레임 추출
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token);
            log.info("Token validated successfully: {}", token);

            // Device-Type 검증
            String tokenDeviceType = claimsJws.getBody().get("deviceType", String.class);
            if (!deviceType.equals(tokenDeviceType)) {
                log.error("Device-Type 미스매칭 : Token deviceType = {}, Header deviceType = {}", tokenDeviceType, deviceType);
                throw new AuthException(JwtErrorCode.INVALID_DEVICE_TYPE);
            }

            return claimsJws;

        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new AuthException(JwtErrorCode.TOKEN_SIGNATURE_ERROR);
        } catch (IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new AuthException(JwtErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
            throw new AuthException(JwtErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
            throw new AuthException(JwtErrorCode.NOT_SUPPORT_TOKEN);
        }
    }
}
