package com.mongoDB.demo.service;

import com.mongoDB.demo.model.Member;

import java.util.List;


public interface RegisterUsersService {

    void registerNewUsers(Member member);

    List<Member> getUsers();

    Member getUser(String id);
}
