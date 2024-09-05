package com.mongoDB.demo.controller;

import com.mongoDB.demo.model.Member;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ErrorControllerTest {

    private final ErrorController errorController = new ErrorController();

    @Test
    void accessDenied_ShouldAddErrorMessageToModel() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Model model = mock(Model.class);
        when(request.getAttribute("errorMessage")).thenReturn("Access Denied");

        String viewName = errorController.accessDenied(request, model);

        verify(model).addAttribute("errorMessage", "Access Denied");
        assertEquals("baseTemplate", viewName);
    }

    @Test
    void accessDenied_ShouldAddNewMemberToModel() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Model model = mock(Model.class);
        Member newMember = new Member();
        when(request.getAttribute("newMember")).thenReturn(newMember);

        String viewName = errorController.accessDenied(request, model);

        verify(model).addAttribute("newMember", newMember);
        assertEquals("baseTemplate", viewName);
    }

    @Test
    void accessDenied_ShouldAddMembersToModel() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Model model = mock(Model.class);
        List<Member> members = Collections.singletonList(new Member());
        when(request.getAttribute("members")).thenReturn(members);

        String viewName = errorController.accessDenied(request, model);

        verify(model).addAttribute("members", members);
        assertEquals("baseTemplate", viewName);
    }
}
