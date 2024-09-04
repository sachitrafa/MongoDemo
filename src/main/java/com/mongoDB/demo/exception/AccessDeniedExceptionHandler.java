package com.mongoDB.demo.exception;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.service.RegisterUsersService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {

    private final RegisterUsersService registerUsersService;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        request.setAttribute("errorMessage", accessDeniedException.getMessage());
        request.setAttribute("newMember",new Member());
        request.setAttribute("members",registerUsersService.getUsers());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error/access-denied");
        dispatcher.forward(request, response);
    }
}
