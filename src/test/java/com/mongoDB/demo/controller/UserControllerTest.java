package com.mongoDB.demo.controller;

import com.mongoDB.demo.model.User;
import com.mongoDB.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final UserController userController = new UserController(userRepository, passwordEncoder);

    @Test
    void showRegistrationForm_ShouldAddNewUserToModel() {
        Model model = mock(Model.class);

        String viewName = userController.showRegistrationForm(model);

        verify(model).addAttribute(eq("user"), any(User.class));
        assertEquals("register", viewName);
    }

    @Test
    void saveUser_ShouldReturnRegisterView_WhenBindingResultHasErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        Model model = mock(Model.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.saveUser(new User(), bindingResult, model);

        assertEquals("register", viewName);
    }

    @Test
    void saveUser_ShouldRedirectToLogin_WhenUserIsSavedSuccessfully() {
        BindingResult bindingResult = mock(BindingResult.class);
        Model model = mock(Model.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        String viewName = userController.saveUser(new User(), bindingResult, model);

        verify(userRepository).save(any(User.class));
        assertEquals("redirect:/login", viewName);
    }

    @Test
    void showLoginForm_ShouldReturnLoginView() {
        String viewName = userController.showLoginForm();

        assertEquals("login", viewName);
    }

}
