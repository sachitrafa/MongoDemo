package com.mongoDB.demo.exception;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.service.RegisterUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final RegisterUsersService registerUsersService;

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("newMember",new Member());
        model.addAttribute("members",registerUsersService.getUsers());
        return "baseTemplate";
    }
}
