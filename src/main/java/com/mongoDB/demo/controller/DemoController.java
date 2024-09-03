package com.mongoDB.demo.controller;

import com.mongoDB.demo.model.Member;
import com.mongoDB.demo.service.RegisterUsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class DemoController {

    private final RegisterUsersService registerUsersService;
    @GetMapping("/view")
    public String showRegistrationForm(Model model) {
        model.addAttribute("newMember", new Member());
        model.addAttribute("members", registerUsersService.getUsers());
        return "baseTemplate"; // The name of your JSP file (e.g., "register.jsp")
    }

    @PostMapping("/admin/register")
    public String registerMember(@ModelAttribute("newMember")@Valid Member member,BindingResult bindingResult ,Model model) {
        // Add the new member to the list
        if(bindingResult.hasErrors()){
            model.addAttribute("members", registerUsersService.getUsers());
            return "baseTemplate";
        }
        registerUsersService.registerNewUsers(member);
        // Add attributes to model to render in the JSP
        model.addAttribute("members", registerUsersService.getUsers());
        model.addAttribute("successMessage", "Member registered successfully!");

        // Redirect to avoid form re-submission on refresh
        return "redirect:/members/view";
    }

    @GetMapping(value = "/view/{id}",produces = "application/json")
    @ResponseBody
    public Member getMember(@PathVariable("id") String id, Model model) {
        // Find the member by username
        return registerUsersService.getUser(id);
    }

    @GetMapping("/view/all")
    @ResponseBody
    public List<Member> getAllMembers() {
        return registerUsersService.getUsers();
    }
}
