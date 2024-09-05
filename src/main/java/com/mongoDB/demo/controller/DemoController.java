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

    @PostMapping("/view/register")
    public String registerMember(@ModelAttribute("newMember")@Valid Member member,BindingResult bindingResult ,Model model) throws Exception {
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

    @GetMapping(value = "/admin/{id}",produces = "application/json")
    @ResponseBody
    public Member getMember(@PathVariable("id") String id) throws Exception {
        // Find the member by username
        return registerUsersService.getUser(id);
    }

    @GetMapping("/admin/all")
    @ResponseBody
    public List<Member> getAllMembers() {
        return registerUsersService.getUsers();
    }

    @GetMapping("/admin/edit/{id}")
    public String editMember(@PathVariable("id") String id, Model model) throws Exception {
        // Find the member by username
        Member member = registerUsersService.getUser(id);
        model.addAttribute("member", member);
        return "editTemplate";
    }

    @PostMapping("/admin/update/{id}")
    public String updateMember(@PathVariable("id") String id ,@ModelAttribute("member") @Valid Member member,BindingResult bindingResult ,Model model) throws Exception {
        if(bindingResult.hasErrors()){
            model.addAttribute("members", registerUsersService.getUsers());
            return "editTemplate";
        }
        registerUsersService.editUser(member,id);
        model.addAttribute("members", registerUsersService.getUsers());
        model.addAttribute("successMessage", "Member registered successfully!");
        // Update the member
        return "redirect:/members/view";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteMember(@PathVariable String id, Model model){
        registerUsersService.deleteUser(id);
        return "redirect:/members/view";
    }
}
