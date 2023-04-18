package com.security.spring_sec.controllers;

import com.security.spring_sec.model.User;
import com.security.spring_sec.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServices userServices;

    @Autowired
    public UserController(UserServices userService) {
        this.userServices = userService;
    }

    @GetMapping()
    public String getUser (Model model, Principal principal){
        User user = userServices.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "users";
    }
}
