package com.mongoDB.demo.service;

import com.mongoDB.demo.model.Member;

public interface EncryptionService {

    Member encryptMember(Member member);

    Member decryptMember(Member member);
}
