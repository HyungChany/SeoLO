package com.c104.seolo.global.security.jwt;

import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.config.JwtProperties;
import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.global.security.exception.JwtErrorCode;
import com.c104.seolo.global.security.jwt.repository.InvalidTokenRepository;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JwtUtils {
    private final JwtProperties jwtProperties;
    private final InvalidTokenRepository invalidTokenRepository;
    private static final ZoneId zoneId = ZoneId.of("Asia/Seoul");
    private String accessSecretKey;
    private String refreshSecretKey;

    @Autowired
    public JwtUtils(JwtProperties jwtProperties, InvalidTokenRepository invalidTokenRepository) {
        this.jwtProperties = jwtProperties;
        this.invalidTokenRepository = invalidTokenRepository;
    }

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

    public String issueAccessToken(AppUser appUser) {
        // JWT에는 유저 기본키, 사번, 회사코드, 권한을 저장한다.
        List<String> authorityList = appUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        String compact = Jwts.builder()
                .setSubject(appUser.getUsername())
                .claim("userId", appUser.getId())
                .claim("companyCode", appUser.getCompanyCode())
                .claim("authorities", authorityList)
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

    public Jws<Claims> validateAccessToken(final String token) {
        try {
            // 토큰의 서명을 검증하고 클레임 추출
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token);
            invalidTokenRepository.findById(token).ifPresent(value -> {
                throw new CommonException(JwtErrorCode.TOKEN_SIGNATURE_ERROR);
            });
            return claimsJws;
        } catch (MalformedJwtException e) {
            log.info("exception : 잘못된 엑세스 토큰 시그니처");
            throw new CommonException(JwtErrorCode.TOKEN_SIGNATURE_ERROR);
        } catch (IllegalArgumentException e) {
            log.info("exception : 잘못된 엑세스 토큰");
            throw new CommonException(JwtErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("exception : 엑세스 토큰 기간 만료");
            throw new CommonException(JwtErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("exception : 지원되지 않는 엑세스 토큰");
            throw new CommonException(JwtErrorCode.NOT_SUPPORT_TOKEN);
        }
    }


}
