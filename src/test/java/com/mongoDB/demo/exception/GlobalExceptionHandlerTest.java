package com.mongoDB.demo.exception;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.service.RegisterUsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    @Mock
    private RegisterUsersService registerUsersService;

    @Mock
    private Model model;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleException_ShouldAddErrorMessageAndMembersToModel() {
        Exception exception = new Exception("Test error message");
        when(registerUsersService.getUsers()).thenReturn(Collections.emptyList());

        String viewName = globalExceptionHandler.handleException(exception, model);

        verify(model).addAttribute("errorMessage", "Test error message");
        verify(model).addAttribute(eq("newMember"), any(Member.class));
        verify(model).addAttribute("members", Collections.emptyList());
        assertEquals("baseTemplate", viewName);
    }

    @Test
    void handleException_ShouldHandleNullExceptionMessage() {
        Exception exception = new Exception();
        when(registerUsersService.getUsers()).thenReturn(Collections.emptyList());

        String viewName = globalExceptionHandler.handleException(exception, model);

        verify(model).addAttribute("errorMessage", null);
        verify(model).addAttribute(eq("newMember"), any(Member.class));
        verify(model).addAttribute("members", Collections.emptyList());
        assertEquals("baseTemplate", viewName);
    }
}
