package com.mongoDB.demo.service;

import com.mongoDB.demo.model.Enum.RoleTypes;
import com.mongoDB.demo.model.User;
import com.mongoDB.demo.repository.UserRepository;
import com.mongoDB.demo.service.impl.CustomUserDetailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CustomUserDetailServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailServiceImpl customUserDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        User user = new User();
        user.setUserName("testUser");
        user.setPassword("password");
        user.setRoles(Set.of());
        when(userRepository.findByUserName("testUser")).thenReturn(user);

        var userDetails = customUserDetailService.loadUserByUsername("testUser");

        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertEquals(0, userDetails.getAuthorities().size());
    }

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findByUserName("nonExistentUser")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailService.loadUserByUsername("nonExistentUser"));
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WithAuthorities() {
        User user = new User();
        user.setUserName("testUser");
        user.setPassword("password");
        user.setRoles(Set.of(RoleTypes.ROLE_USER));
        when(userRepository.findByUserName("testUser")).thenReturn(user);

        var userDetails = customUserDetailService.loadUserByUsername("testUser");

        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }
}
