package com.mongoDB.demo.exception;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.service.RegisterUsersService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class AccessDeniedExceptionHandlerTest {

    private final RegisterUsersService registerUsersService = mock(RegisterUsersService.class);
    private final AccessDeniedExceptionHandler handler = new AccessDeniedExceptionHandler(registerUsersService);

    @Test
    void handle_ShouldSetErrorMessageAttribute() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AccessDeniedException exception = new AccessDeniedException("Access Denied");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/error/access-denied")).thenReturn(dispatcher);

        handler.handle(request, response, exception);

        verify(request).setAttribute("errorMessage", "Access Denied");
    }

    @Test
    void handle_ShouldSetNewMemberAttribute() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AccessDeniedException exception = new AccessDeniedException("Access Denied");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/error/access-denied")).thenReturn(dispatcher);

        handler.handle(request, response, exception);

        verify(request).setAttribute(eq("newMember"), any(Member.class));
    }

    @Test
    void handle_ShouldSetMembersAttribute() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AccessDeniedException exception = new AccessDeniedException("Access Denied");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/error/access-denied")).thenReturn(dispatcher);
        when(registerUsersService.getUsers()).thenReturn(Collections.emptyList());

        handler.handle(request, response, exception);

        verify(request).setAttribute("members", Collections.emptyList());
    }

    @Test
    void handle_ShouldForwardRequest() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AccessDeniedException exception = new AccessDeniedException("Access Denied");
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/error/access-denied")).thenReturn(dispatcher);

        handler.handle(request, response, exception);

        verify(dispatcher).forward(request, response);
    }
}
