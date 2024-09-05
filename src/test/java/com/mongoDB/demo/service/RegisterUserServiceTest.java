package com.mongoDB.demo.service;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.repository.RegisterUserRepository;
import com.mongoDB.demo.service.impl.RegisterUsersServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegisterUserServiceTest {

    private final RegisterUserRepository registerUserRepository = mock(RegisterUserRepository.class);
    private final PersonalInformationValidationService personalInformationValidationService = mock(PersonalInformationValidationService.class);
    private final DeterministicEncryptionService deterministicEncryptionService = mock(DeterministicEncryptionService.class);
    private final RegisterUsersServiceImpl registerUsersService = new RegisterUsersServiceImpl(registerUserRepository, personalInformationValidationService, deterministicEncryptionService);

    @Test
    void registerNewUsers_ShouldEncryptAndSaveMember() throws Exception {
        Member member = new Member();
        member.setEmail("test@example.com");
        member.setPhoneNumber("1234567890");
        when(deterministicEncryptionService.encrypt("test@example.com")).thenReturn("encryptedEmail");
        when(deterministicEncryptionService.encrypt("1234567890")).thenReturn("encryptedPhoneNumber");

        registerUsersService.registerNewUsers(member);

        verify(deterministicEncryptionService).encrypt("test@example.com");
        verify(deterministicEncryptionService).encrypt("1234567890");
        verify(personalInformationValidationService).validatePersonalInformation("encryptedEmail", "encryptedPhoneNumber", false, false);
        verify(registerUserRepository).save(member);
    }

    @Test
    void getUsers_ShouldReturnDecryptedMembers() {
        Member encryptedMember = new Member();
        when(registerUserRepository.findAll()).thenReturn(Collections.singletonList(encryptedMember));
        Member decryptedMember = new Member();
        when(deterministicEncryptionService.decryptMember(encryptedMember)).thenReturn(decryptedMember);

        List<Member> result = registerUsersService.getUsers();

        assertFalse(CollectionUtils.isEmpty(result));
        assertEquals(decryptedMember, result.get(0));
    }

    @Test
    void getUsers_ShouldReturnEmptyList_WhenNoMembersFound() {
        when(registerUserRepository.findAll()).thenReturn(Collections.emptyList());

        List<Member> result = registerUsersService.getUsers();

        assertTrue(CollectionUtils.isEmpty(result));
    }

    @Test
    void getUser_ShouldReturnDecryptedMember_WhenMemberExists() {
        Member encryptedMember = new Member();
        when(registerUserRepository.findById("1")).thenReturn(Optional.of(encryptedMember));
        Member decryptedMember = new Member();
        when(deterministicEncryptionService.decryptMember(encryptedMember)).thenReturn(decryptedMember);

        Member result = registerUsersService.getUser("1");

        assertEquals(decryptedMember, result);
    }

    @Test
    void getUser_ShouldReturnNull_WhenMemberDoesNotExist() {
        when(registerUserRepository.findById("1")).thenReturn(Optional.empty());

        Member result = registerUsersService.getUser("1");

        assertNull(result);
    }

    @Test
    void editUser_ShouldEncryptAndSaveMember() throws Exception {
        Member member = new Member();
        member.setEmail("test@example.com");
        member.setPhoneNumber("1234567890");
        Member existingMember = new Member();
        existingMember.setEmail("existing@example.com");
        existingMember.setPhoneNumber("0987654321");
        when(registerUserRepository.findById("1")).thenReturn(Optional.of(existingMember));
        when(deterministicEncryptionService.encrypt("test@example.com")).thenReturn("encryptedEmail");
        when(deterministicEncryptionService.encrypt("1234567890")).thenReturn("encryptedPhoneNumber");

        registerUsersService.editUser(member, "1");

        verify(deterministicEncryptionService).encrypt("test@example.com");
        verify(deterministicEncryptionService).encrypt("1234567890");
        verify(personalInformationValidationService).validatePersonalInformation("encryptedEmail", "encryptedPhoneNumber", false, false);
        verify(registerUserRepository).save(member);
    }

    @Test
    void deleteUser_ShouldDeleteMember() {
        registerUsersService.deleteUser("1");

        verify(registerUserRepository).deleteById("1");
    }
}
