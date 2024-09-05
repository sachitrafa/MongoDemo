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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


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
    void testShowRegistrationForm() {
        // Arrange
        List<Member> members = new ArrayList<>();
        when(registerUsersService.getUsers()).thenReturn(members);

        // Act
        String result = demoController.showRegistrationForm(model);

        // Assert
        verify(model).addAttribute(eq("newMember"), any(Member.class));
        verify(model).addAttribute("members", members);
        assertEquals("baseTemplate", result);
    }

    @Test
    void testRegisterMemberWithErrors() throws Exception {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);
        List<Member> members = new ArrayList<>();
        when(registerUsersService.getUsers()).thenReturn(members);
        Member member = new Member();

        // Act
        String result = demoController.registerMember(member, bindingResult, model);

        // Assert
        verify(model).addAttribute("members", members);
        assertEquals("baseTemplate", result);
    }

    @Test
    void testRegisterMemberWithoutErrors() throws Exception {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        List<Member> members = new ArrayList<>();
        when(registerUsersService.getUsers()).thenReturn(members);
        Member member = new Member();

        // Act
        String result = demoController.registerMember(member, bindingResult, model);

        // Assert
        verify(registerUsersService).registerNewUsers(member);
        assertEquals("redirect:/members/view", result);
    }

    @Test
    void testGetMember() throws Exception {
        // Arrange
        Member member = new Member();
        when(registerUsersService.getUser(anyString())).thenReturn(member);

        // Act
        Member result = demoController.getMember("1");

        // Assert
        assertEquals(member, result);
    }

    @Test
    void testGetAllMembers() {
        // Arrange
        List<Member> members = new ArrayList<>();
        when(registerUsersService.getUsers()).thenReturn(members);

        // Act
        List<Member> result = demoController.getAllMembers();

        // Assert
        assertEquals(members, result);
    }

    @Test
    void testEditMember() throws Exception {
        // Arrange
        Member member = new Member();
        when(registerUsersService.getUser(anyString())).thenReturn(member);

        // Act
        String result = demoController.editMember("1", model);

        // Assert
        verify(model).addAttribute("member", member);
        assertEquals("editTemplate", result);
    }

    @Test
    void testUpdateMemberWithErrors() throws Exception {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);
        List<Member> members = new ArrayList<>();
        when(registerUsersService.getUsers()).thenReturn(members);
        Member member = new Member();

        // Act
        String result = demoController.updateMember("1", member, bindingResult, model);

        // Assert
        verify(model).addAttribute("members", members);
        assertEquals("editTemplate", result);
    }

    @Test
    void testUpdateMemberWithoutErrors() throws Exception {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        List<Member> members = new ArrayList<>();
        when(registerUsersService.getUsers()).thenReturn(members);
        Member member = new Member();

        // Act
        String result = demoController.updateMember("1", member, bindingResult, model);

        // Assert
        verify(registerUsersService).editUser(member, "1");
        assertEquals("redirect:/members/view", result);
    }

    @Test
    void testDeleteMember() {
        // Act
        String result = demoController.deleteMember("1", model);

        // Assert
        verify(registerUsersService).deleteUser("1");
        assertEquals("redirect:/members/view", result);
    }


}
