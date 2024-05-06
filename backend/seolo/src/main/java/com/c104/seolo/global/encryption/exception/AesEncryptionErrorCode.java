package com.c104.seolo.global.encryption.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AesEncryptionErrorCode {
    ENCRYPTION_ERROR("AE01", "AES 암호화 중 오류가 발생했습니다.", HttpStatus.BAD_REQUEST),
    DECRYPTION_ERROR("AE02", "AES 복호화 중 오류가 발생했습니다.", HttpStatus.BAD_REQUEST),
    KEY_GENERATION_ERROR("AE03", "대칭키 생성 중 오류가 발생했습니다.", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
