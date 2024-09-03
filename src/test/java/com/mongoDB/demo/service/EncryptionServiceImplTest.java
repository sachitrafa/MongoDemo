package com.mongoDB.demo.service;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.service.impl.EncryptionServiceImpl;
import org.jasypt.util.text.TextEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EncryptionServiceImplTest {

    @Mock
    private TextEncryptor textEncryptor;

    @InjectMocks
    private EncryptionServiceImpl encryptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void encryptMember_ShouldEncryptEmailAndPhoneNumber() {
        Member member = new Member();
        member.setEmail("test@example.com");
        member.setPhoneNumber("1234567890");

        when(textEncryptor.encrypt("test@example.com")).thenReturn("encryptedEmail");
        when(textEncryptor.encrypt("1234567890")).thenReturn("encryptedPhoneNumber");

        Member encryptedMember = encryptionService.encryptMember(member);

        assertEquals("encryptedEmail", encryptedMember.getEmail());
        assertEquals("encryptedPhoneNumber", encryptedMember.getPhoneNumber());
    }

    @Test
    void decryptMember_ShouldDecryptEmailAndPhoneNumber() {
        Member member = new Member();
        member.setEmail("encryptedEmail");
        member.setPhoneNumber("encryptedPhoneNumber");

        when(textEncryptor.decrypt("encryptedEmail")).thenReturn("test@example.com");
        when(textEncryptor.decrypt("encryptedPhoneNumber")).thenReturn("1234567890");

        Member decryptedMember = encryptionService.decryptMember(member);

        assertEquals("test@example.com", decryptedMember.getEmail());
        assertEquals("1234567890", decryptedMember.getPhoneNumber());
    }

    @Test
    void encryptMember_ShouldHandleNullEmailAndPhoneNumber() {
        Member member = new Member();
        member.setEmail(null);
        member.setPhoneNumber(null);

        when(textEncryptor.encrypt(null)).thenReturn(null);

        Member encryptedMember = encryptionService.encryptMember(member);

        assertEquals(null, encryptedMember.getEmail());
        assertEquals(null, encryptedMember.getPhoneNumber());
    }

    @Test
    void decryptMember_ShouldHandleNullEmailAndPhoneNumber() {
        Member member = new Member();
        member.setEmail(null);
        member.setPhoneNumber(null);

        when(textEncryptor.decrypt(null)).thenReturn(null);

        Member decryptedMember = encryptionService.decryptMember(member);

        assertEquals(null, decryptedMember.getEmail());
        assertEquals(null, decryptedMember.getPhoneNumber());
    }

}
