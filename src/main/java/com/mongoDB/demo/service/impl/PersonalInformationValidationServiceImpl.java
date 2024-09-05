package com.mongoDB.demo.service.impl;

import com.mongoDB.demo.repository.RegisterUserRepository;
import com.mongoDB.demo.service.PersonalInformationValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalInformationValidationServiceImpl implements PersonalInformationValidationService {

    private final RegisterUserRepository registerUserRepository;

    @Override
    public void validatePersonalInformation(String email, String phoneNumber, boolean skipEmail, boolean skipPhoneNumber) {
        if(validateEmail(email) && !skipEmail){
            throw new IllegalArgumentException("Email already exists");
        }
        if(validatePhoneNumber(phoneNumber) && !skipPhoneNumber){
            throw new IllegalArgumentException("Phone number already exists");
        }
    }

    private boolean validateEmail(String email) {
        return registerUserRepository.findByEmail(email) != null;
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        return registerUserRepository.findByPhoneNumber(phoneNumber) != null;
    }
}
