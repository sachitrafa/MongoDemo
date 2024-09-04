package com.mongoDB.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/access-denied")
    public String accessDenied(HttpServletRequest request, Model model){
        model.addAttribute("errorMessage", request.getAttribute("errorMessage"));
        model.addAttribute("newMember", request.getAttribute("newMember"));
        model.addAttribute("members", request.getAttribute("members"));
        return "baseTemplate";
    }
}
