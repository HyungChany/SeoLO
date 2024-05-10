package com.c104.seolo.global.encryption;

import com.c104.seolo.global.encryption.exception.AesEncryptionErrorCode;
import com.c104.seolo.global.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
public class AesEncryption {
    private static final String ALGORITHM = "AES";
    private static final int KEYSIZE = 128;


    // 랜덤 대칭키 생성
    public static SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(KEYSIZE);
            return keyGenerator.generateKey();
        } catch (Exception e) {
            log.error("대칭키 생성 중 오류가 발생" + e.getMessage());
            throw new CommonException(AesEncryptionErrorCode.KEY_GENERATION_ERROR);
        }
    }
    
    // 대칭키 DB 저장을 위한 Base64 인코딩
    public static String getBase64EncodedKey(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static SecretKey decodeBase64ToSecretKey(String base64EncodedKey) {
        // Base64 인코딩된 키를 디코드하여 바이트 배열로 변환
        byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);

        // 바이트 배열을 사용하여 SecretKey 객체를 생성
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
        return originalKey;
    }

    public static byte[] encrypt(String data, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            log.error("AES 암호화 중 에러발생: " + e.getMessage());
            throw new CommonException(AesEncryptionErrorCode.ENCRYPTION_ERROR);
        }
    }


    public static String decrypt(byte[] encryptedData, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedData = cipher.doFinal(encryptedData);
            return new String(decryptedData);
        } catch (Exception e) {
            log.error("AES 복호화 중 에러발생: " + e.getMessage());
            throw new CommonException(AesEncryptionErrorCode.DECRYPTION_ERROR);
        }
    }
}
