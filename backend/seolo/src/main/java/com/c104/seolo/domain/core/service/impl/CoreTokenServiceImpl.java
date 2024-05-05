package com.c104.seolo.domain.core.service.impl;

import com.c104.seolo.domain.core.dto.LockerDto;
import com.c104.seolo.domain.core.entity.Locker;
import com.c104.seolo.domain.core.entity.Token;
import com.c104.seolo.domain.core.exception.CoreTokenErrorCode;
import com.c104.seolo.domain.core.repository.TokenRepository;
import com.c104.seolo.domain.core.service.LockerService;
import com.c104.seolo.domain.core.service.TokenService;
import com.c104.seolo.domain.user.entity.AppUser;
import com.c104.seolo.global.encryption.AesEncryption;
import com.c104.seolo.global.exception.CommonException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;

@Service
public class CoreTokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final LockerService lockerService;

    @Autowired
    public CoreTokenServiceImpl(TokenRepository tokenRepository, LockerService lockerService) {
        this.tokenRepository = tokenRepository;
        this.lockerService = lockerService;
    }


    @Override
    @Transactional
    public Token issueCoreAuthToken(AppUser appUser , String lockerUid) {
        if (isTokenExistedForUserId(appUser.getId())) {
            throw new CommonException(CoreTokenErrorCode.EXISTED_TOKEN);
        }

        // 토큰의 value는 자물쇠의 고유 넘버(UID 혹은 serial No.) 으로 지정한다.
        // 해싱에 쓰이는 대칭키는 랜덤으로 생성해서 Locker DB에 저장한 값으로 한다.
        Locker locker = lockerService.getLockerByUid(lockerUid);
        String base64encryptionKey = locker.getEncryptionKey();
        SecretKey encryptionKey = AesEncryption.decodeBase64ToSecretKey(base64encryptionKey);

        String encrypt = AesEncryption.encrypt(lockerUid, encryptionKey);


        return Token.builder()
                .tokenValue(encrypt)
                .locker(locker)
                .appUser(appUser)
                .build();
    }

    @Override
    public boolean isTokenExistedForUserId(Long userId) {
        return tokenRepository.findByAppUserId(userId).isPresent();
    }
}
