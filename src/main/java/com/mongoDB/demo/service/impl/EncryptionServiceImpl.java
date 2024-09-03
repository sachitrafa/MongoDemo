package com.mongoDB.demo.service.impl;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.jasypt.util.text.TextEncryptor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptionServiceImpl implements EncryptionService {

    private final TextEncryptor textEncryptor;

    @Override
    public Member encryptMember(Member member) {
        member.setEmail(textEncryptor.encrypt(member.getEmail()));
        member.setPhoneNumber(textEncryptor.encrypt(member.getPhoneNumber()));
        return member;
    }

    @Override
    public Member decryptMember(Member member) {
        member.setEmail(textEncryptor.decrypt(member.getEmail()));
        member.setPhoneNumber(textEncryptor.decrypt(member.getPhoneNumber()));
        return member;
    }
}
