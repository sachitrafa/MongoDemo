package com.mongoDB.demo.service.impl;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.service.DeterministicEncryptionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class DeterministicEncryptionServiceImpl implements DeterministicEncryptionService {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String AES = "AES";
    private final SecretKeySpec secretKey = new SecretKeySpec(normalizeKey("1234567890123456"), AES);


    @Override
    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @Override
    public String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    @Override
    public Member decryptMember(Member member) {
        try {
            member.setEmail(decrypt(member.getEmail()));
            member.setPhoneNumber(decrypt(member.getPhoneNumber()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return member;
    }

    private byte[] normalizeKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        int length = keyBytes.length;

        // Pad or truncate key to 16 bytes (AES-128)
        if (length < 16) {
            byte[] paddedKey = new byte[16];
            System.arraycopy(keyBytes, 0, paddedKey, 0, length);
            return paddedKey;
        } else if (length < 24) {
            byte[] paddedKey = new byte[24];
            System.arraycopy(keyBytes, 0, paddedKey, 0, length);
            return paddedKey;
        } else if (length < 32) {
            byte[] paddedKey = new byte[32];
            System.arraycopy(keyBytes, 0, paddedKey, 0, length);
            return paddedKey;
        } else {
            // Truncate to 32 bytes for AES-256
            byte[] truncatedKey = new byte[32];
            System.arraycopy(keyBytes, 0, truncatedKey, 0, 32);
            return truncatedKey;
        }
    }
}
