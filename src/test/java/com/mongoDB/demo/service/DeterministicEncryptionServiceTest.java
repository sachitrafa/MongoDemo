package com.mongoDB.demo.service;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.service.impl.DeterministicEncryptionServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeterministicEncryptionServiceTest {

    private final DeterministicEncryptionServiceImpl encryptionService = new DeterministicEncryptionServiceImpl();

    @Test
    void encrypt_ShouldReturnEncryptedString() throws Exception {
        String plainText = "HelloWorld";
        String encryptedText = encryptionService.encrypt(plainText);

        assertNotNull(encryptedText);
        assertNotEquals(plainText, encryptedText);
    }

    @Test
    void decrypt_ShouldReturnOriginalString() throws Exception {
        String plainText = "HelloWorld";
        String encryptedText = encryptionService.encrypt(plainText);
        String decryptedText = encryptionService.decrypt(encryptedText);

        assertEquals(plainText, decryptedText);
    }

    @Test
    void decryptMember_ShouldDecryptMemberFields() throws Exception {
        Member member = new Member();
        member.setEmail(encryptionService.encrypt("test@example.com"));
        member.setPhoneNumber(encryptionService.encrypt("1234567890"));

        Member decryptedMember = encryptionService.decryptMember(member);

        assertEquals("test@example.com", decryptedMember.getEmail());
        assertEquals("1234567890", decryptedMember.getPhoneNumber());
    }

    @Test
    void encrypt_ShouldThrowExceptionForNullInput() {
        assertThrows(NullPointerException.class, () -> encryptionService.encrypt(null));
    }

    @Test
    void decrypt_ShouldThrowExceptionForInvalidInput() {
        assertThrows(Exception.class, () -> encryptionService.decrypt("InvalidEncryptedText"));
    }

}
