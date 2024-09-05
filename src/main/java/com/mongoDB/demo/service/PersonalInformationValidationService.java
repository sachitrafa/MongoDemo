package com.mongoDB.demo.service;

public interface PersonalInformationValidationService {

    void validatePersonalInformation(String email,String phoneNumber, boolean skipEmail, boolean skipPhoneNumber);
}
