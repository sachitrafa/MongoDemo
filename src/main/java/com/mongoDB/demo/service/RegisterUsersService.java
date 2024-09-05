package com.mongoDB.demo.service;

import com.mongoDB.demo.model.Member;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public interface RegisterUsersService {

    void registerNewUsers(Member member) throws Exception;

    List<Member> getUsers();

    Member getUser(String id);

    void editUser(Member member ,String id) throws Exception;

    void deleteUser(String id);
}
