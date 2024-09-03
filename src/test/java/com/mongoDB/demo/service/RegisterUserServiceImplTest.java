package com.mongoDB.demo.service;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.repository.RegisterUserRepository;
import com.mongoDB.demo.service.impl.RegisterUsersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegisterUserServiceImplTest {

    @Mock
    private RegisterUserRepository registerUserRepository;

    @Mock
    private EncryptionService encryptionService;

    @InjectMocks
    private RegisterUsersServiceImpl registerUsersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerNewUsers_ShouldSaveEncryptedMember() {
        Member member = new Member();
        Member encryptedMember = new Member();
        when(encryptionService.encryptMember(member)).thenReturn(encryptedMember);

        registerUsersService.registerNewUsers(member);

        verify(registerUserRepository).save(encryptedMember);
    }

    @Test
    void getUsers_ShouldReturnDecryptedMembers_WhenMembersExist() {
        Member encryptedMember = new Member();
        Member decryptedMember = new Member();
        when(registerUserRepository.findAll()).thenReturn(List.of(encryptedMember));
        when(encryptionService.decryptMember(encryptedMember)).thenReturn(decryptedMember);

        List<Member> result = registerUsersService.getUsers();

        assertEquals(List.of(decryptedMember), result);
    }

    @Test
    void getUsers_ShouldReturnEmptyList_WhenNoMembersExist() {
        when(registerUserRepository.findAll()).thenReturn(Collections.emptyList());

        List<Member> result = registerUsersService.getUsers();

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void getUser_ShouldReturnDecryptedMember_WhenMemberExists() {
        Member encryptedMember = new Member();
        Member decryptedMember = new Member();
        when(registerUserRepository.findById("1")).thenReturn(Optional.of(encryptedMember));
        when(encryptionService.decryptMember(encryptedMember)).thenReturn(decryptedMember);

        Member result = registerUsersService.getUser("1");

        assertEquals(decryptedMember, result);
    }

    @Test
    void getUser_ShouldReturnNull_WhenMemberDoesNotExist() {
        when(registerUserRepository.findById("1")).thenReturn(Optional.empty());

        Member result = registerUsersService.getUser("1");

        assertNull(result);
    }
}
