package com.mongoDB.demo.service.impl;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.repository.RegisterUserRepository;
import com.mongoDB.demo.service.EncryptionService;
import com.mongoDB.demo.service.RegisterUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterUsersServiceImpl implements RegisterUsersService {

    private final RegisterUserRepository registerUserRepository;
    private final EncryptionService encryptionService;

    @Override
    public void registerNewUsers(Member member) {
        registerUserRepository.save(encryptionService.encryptMember(member));
    }

    @Override
    public List<Member> getUsers() {
        List<Member> memberList = registerUserRepository.findAll();
        if(CollectionUtils.isEmpty(memberList)){
            return List.of();
        }
        return memberList.stream().map(encryptionService::decryptMember).toList();
    }

    @Override
    public Member getUser(String id) {
        Member member = registerUserRepository.findById(id).orElse(null);
        if(member != null){
            member = encryptionService.decryptMember(member);
        }
        return member;
    }
}
