package com.mongoDB.demo.service;

import com.mongoDB.demo.model.Member;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface DeterministicEncryptionService {

    String encrypt(String plainText) throws Exception;

    String decrypt(String encryptedText) throws Exception;

    Member decryptMember(Member member);
}
