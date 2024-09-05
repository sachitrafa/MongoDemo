package com.mongoDB.demo.service;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.repository.RegisterUserRepository;
import com.mongoDB.demo.service.impl.PersonalInformationValidationServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonalInformationValidationServiceTest {

    private final RegisterUserRepository registerUserRepository = mock(RegisterUserRepository.class);
    private final PersonalInformationValidationServiceImpl validationService = new PersonalInformationValidationServiceImpl(registerUserRepository);

    @Test
    void validatePersonalInformation_ShouldThrowException_WhenEmailExistsAndNotSkipped() {
        when(registerUserRepository.findByEmail("test@example.com")).thenReturn(new Member());

        assertThrows(IllegalArgumentException.class, () ->
                validationService.validatePersonalInformation("test@example.com", "1234567890", false, true)
        );
    }

    @Test
    void validatePersonalInformation_ShouldThrowException_WhenPhoneNumberExistsAndNotSkipped() {
        when(registerUserRepository.findByPhoneNumber("1234567890")).thenReturn(new Member());

        assertThrows(IllegalArgumentException.class, () ->
                validationService.validatePersonalInformation("test@example.com", "1234567890", true, false)
        );
    }

    @Test
    void validatePersonalInformation_ShouldNotThrowException_WhenEmailExistsAndSkipped() {
        when(registerUserRepository.findByEmail("test@example.com")).thenReturn(new Member());

        validationService.validatePersonalInformation("test@example.com", "1234567890", true, true);
    }

    @Test
    void validatePersonalInformation_ShouldNotThrowException_WhenPhoneNumberExistsAndSkipped() {
        when(registerUserRepository.findByPhoneNumber("1234567890")).thenReturn(new Member());

        validationService.validatePersonalInformation("test@example.com", "1234567890", true, true);
    }

    @Test
    void validatePersonalInformation_ShouldNotThrowException_WhenEmailAndPhoneNumberDoNotExist() {
        when(registerUserRepository.findByEmail("test@example.com")).thenReturn(null);
        when(registerUserRepository.findByPhoneNumber("1234567890")).thenReturn(null);

        validationService.validatePersonalInformation("test@example.com", "1234567890", false, false);
    }
}
