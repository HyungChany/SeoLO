package com.c104.seolo.domain.core.service.impl;

import com.c104.seolo.domain.core.dto.LockerDto;
import com.c104.seolo.domain.core.entity.Token;
import com.c104.seolo.domain.core.exception.CoreTokenErrorCode;
import com.c104.seolo.domain.core.repository.TokenRepository;
import com.c104.seolo.domain.core.service.LockerService;
import com.c104.seolo.domain.core.service.TokenService;
import com.c104.seolo.global.exception.CommonException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Token issueCoreAuthToken(Long userId ,String lockerUid) {
        if (isTokenExistedForUserId(userId)) {
            throw new CommonException(CoreTokenErrorCode.EXISTED_TOKEN);
        }

        // 토큰의 value는 자물쇠의 고유 넘버(UID 혹은 serial No.) 으로 지정한다.
        String LockerPassword = lockerUid;
        LockerDto locker = lockerService.getLockerByUid(lockerUid);

        // 해싱에 필 랜덤으로 생성해서 DB에 저장한 키로 한다.
//        SecretKey randomSecretKey = AesEncryption.generateKey();
//        String encrypt = AesEncryption.encrypt(lockerUid, locker.getEncryptionKey());


        return Token.builder()
                .tokenValue("test")
                .build();

    }

    @Override
    public boolean isTokenExistedForUserId(Long userId) {
        return tokenRepository.findByAppUserId(userId).isPresent();
    }
}
