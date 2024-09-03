package com.mongoDB.demo.controller;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.service.RegisterUsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DemoControllerTest {

    @Mock
    private RegisterUsersService registerUsersService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private DemoController demoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showRegistrationForm_ShouldAddNewMemberAndMembersToModel() {
        when(registerUsersService.getUsers()).thenReturn(Collections.emptyList());

        String viewName = demoController.showRegistrationForm(model);

        verify(model).addAttribute(eq("newMember"), any(Member.class));
        verify(model).addAttribute("members", Collections.emptyList());
        assertEquals("baseTemplate", viewName);
    }

    @Test
    void registerMember_ShouldReturnBaseTemplate_WhenBindingResultHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);
        when(registerUsersService.getUsers()).thenReturn(Collections.emptyList());

        String viewName = demoController.registerMember(new Member(), bindingResult, model);

        verify(model).addAttribute("members", Collections.emptyList());
        assertEquals("baseTemplate", viewName);
    }

    @Test
    void registerMember_ShouldRedirectToView_WhenMemberIsRegisteredSuccessfully() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(registerUsersService.getUsers()).thenReturn(Collections.emptyList());

        String viewName = demoController.registerMember(new Member(), bindingResult, model);

        verify(registerUsersService).registerNewUsers(any(Member.class));
        assertEquals("redirect:/members/view", viewName);
    }

    @Test
    void getMember_ShouldReturnMemberById() {
        Member member = new Member();
        when(registerUsersService.getUser("1")).thenReturn(member);

        Member result = demoController.getMember("1", model);

        assertEquals(member, result);
    }

    @Test
    void getAllMembers_ShouldReturnAllMembers() {
        List<Member> members = Collections.singletonList(new Member());
        when(registerUsersService.getUsers()).thenReturn(members);

        List<Member> result = demoController.getAllMembers();

        assertEquals(members, result);
    }
}
