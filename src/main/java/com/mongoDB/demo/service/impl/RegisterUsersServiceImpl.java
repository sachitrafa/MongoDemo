package com.mongoDB.demo.service.impl;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.repository.RegisterUserRepository;
import com.mongoDB.demo.service.DeterministicEncryptionService;
import com.mongoDB.demo.service.PersonalInformationValidationService;
import com.mongoDB.demo.service.RegisterUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterUsersServiceImpl implements RegisterUsersService {

    private final RegisterUserRepository registerUserRepository;
    private final PersonalInformationValidationService personalInformationValidationService;
    private final DeterministicEncryptionService deterministicEncryptionService;

    @Override
    public void registerNewUsers(Member member) throws Exception {
        member.setEmail(deterministicEncryptionService.encrypt(member.getEmail()));
        member.setPhoneNumber(deterministicEncryptionService.encrypt(member.getPhoneNumber()));
        personalInformationValidationService.validatePersonalInformation(member.getEmail(), member.getPhoneNumber(),false,false);
        registerUserRepository.save(member);
    }



    @Override
    public List<Member> getUsers()  {
        List<Member> memberList = registerUserRepository.findAll();
        if(CollectionUtils.isEmpty(memberList)){
            return List.of();
        }
        return memberList.stream().map(deterministicEncryptionService::decryptMember).toList();
    }


    @Override
    public Member getUser(String id) {
        Member member = registerUserRepository.findById(id).orElse(null);
        if(member != null){
            member = deterministicEncryptionService.decryptMember(member);
        }
        return member;
    }

    @Override
    public void editUser(Member member, String id) throws Exception {
        member.setEmail(deterministicEncryptionService.encrypt(member.getEmail()));
        member.setPhoneNumber(deterministicEncryptionService.encrypt(member.getPhoneNumber()));
        Member existingMember = registerUserRepository.findById(id).orElse(null);
        boolean emailSkip = existingMember.getEmail().equals(member.getEmail());
        boolean phoneNumberSkip = existingMember.getPhoneNumber().equals(member.getPhoneNumber());
        personalInformationValidationService.validatePersonalInformation(member.getEmail(), member.getPhoneNumber(), emailSkip, phoneNumberSkip);
        registerUserRepository.save(member);
    }

    @Override
    public void deleteUser(String id) {
        registerUserRepository.deleteById(id);
    }
}
