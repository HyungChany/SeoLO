package com.c104.seolo.domain.core.service.impl;

import com.c104.seolo.domain.core.dto.TokenDto;
import com.c104.seolo.domain.core.entity.Locker;
import com.c104.seolo.domain.core.entity.Token;
import com.c104.seolo.domain.core.exception.CoreTokenErrorCode;
import com.c104.seolo.domain.core.repository.TokenRepository;
import com.c104.seolo.domain.core.service.CoreTokenService;
import com.c104.seolo.domain.core.service.LockerService;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.encryption.AesEncryption;
import com.c104.seolo.global.exception.CommonException;
import com.c104.seolo.global.security.jwt.entity.CCodePrincipal;
import com.c104.seolo.global.security.service.DBUserDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoreTokenServiceImpl implements CoreTokenService {

    private final TokenRepository tokenRepository;
    private final LockerService lockerService;
    private final DBUserDetailService dbUserDetailService;



    @Override
    @Transactional
    public TokenDto issueCoreAuthToken(CCodePrincipal cCodePrincipal , String lockerUid) {
        AppUser appUser = dbUserDetailService.loadUserById(cCodePrincipal.getId());
        if (isTokenExistedForUserId(appUser.getId())) {
            throw new CommonException(CoreTokenErrorCode.EXISTED_TOKEN);
        }

        /*
        [자물쇠토큰 발급 모듈]
        1. 자물쇠토큰의 값은 랜덤으로 생성한다.
        2. 자물쇠토큰은 프론트로 넘길 때 대칭키를 통해 암호화한 후 넘긴다.
        대칭키는 자물쇠DB의 locker_encryption_key로 이 값은 자물쇠 등록시에 아두이노에도 저장되어있다.
        자물쇠(아두이노)는 받은 자물쇠 토큰을 대칭키를 통해 복호화해야 비교한다.
        */

        // 1. 자물쇠 토큰을 랜덤으로 생성한다.
        String newCoreToken = UUID.randomUUID().toString();

        // 2. 생성한 토큰을 대칭키로 AES128 암호화한다.
        // Locker DB에 Base64로 저장되어 있는 대칭키를 가져온다.
        Locker locker = lockerService.getLockerByUid(lockerUid);
        String base64encryptionKey = locker.getEncryptionKey();
        // 해당 대칭키 Base64를 복호화한다.
        SecretKey encryptionKey = AesEncryption.decodeBase64ToSecretKey(base64encryptionKey);

        // 복호화한 해당 대칭키를 이용해 자물쇠고유번호를 AES128 암호화한다.
        byte[] encryptedCoreToken = AesEncryption.encrypt(newCoreToken, encryptionKey);

        // AES128 암호화한 토큰을 Base64로 인코딩한다.
        String base64EncryptedCoreToken = Base64.getEncoder().encodeToString(encryptedCoreToken);
        log.info("encryptedCoreToken :{}",encryptedCoreToken);

        // 중복 검사
        if (tokenRepository.findByTokenValue(base64EncryptedCoreToken).isPresent()) {
            throw new CommonException(CoreTokenErrorCode.DUPLICATE_TOKEN_VALUE);
        }

        Token newToken = Token.builder()
                .tokenValue(base64EncryptedCoreToken)
                .locker(locker)
                .appUser(appUser)
                .build();
        tokenRepository.save(newToken);
        return TokenDto.of(newToken);
    }

    @Override
    public void deleteTokenByUserId(Long userId) {
        tokenRepository.findByAppUserId(userId).ifPresentOrElse(
                tokenRepository::delete,
                () -> {
                    throw new CommonException(CoreTokenErrorCode.NOT_EXIST_TOKEN);
                }
        );    }

    @Override
    public boolean isTokenExistedForUserId(Long userId) {
        return tokenRepository.findByAppUserId(userId).isPresent();
    }

    @Override
    public void deleteTokenByTokenValue(String tokenValue) {
        tokenRepository.findById(tokenValue).ifPresentOrElse(
                tokenRepository::delete,
                () -> {
                    throw new CommonException(CoreTokenErrorCode.NOT_EXIST_TOKEN);
                }
        );
    }

    @Override
    public boolean validateTokenWithUser(String tokenValue, Long userId) {
        // 토큰을 발급받은 유저인지 검증
        Token token = tokenRepository.findById(tokenValue).orElseThrow(
                () -> new CommonException(CoreTokenErrorCode.NOT_EXIST_TOKEN)
        );

        return token.getAppUser().getId().equals(userId);
    }

    @Override
    public boolean validateTokenWithLocker(String tokenValue, String lockerId) {
        // 토큰을 발급받은 자물쇠인지 검증
        Token token = tokenRepository.findById(tokenValue).orElseThrow(
                () -> new CommonException(CoreTokenErrorCode.NOT_EXIST_TOKEN)
        );

        return token.getLocker().getUid().equals(lockerId);
    }
}
