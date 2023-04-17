package com.security.spring_sec.controllers;


import com.security.spring_sec.model.User;
import com.security.spring_sec.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;

@Controller
public class UserController {

    private final UserServices userServices;

    @Autowired
    public UserController(UserServices userService) {
        this.userServices = userService;
    }

    @GetMapping(value = "/user")
    public String getUser (Model model, Principal principal){
        User user = userServices.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "users";
    }

    @GetMapping(value = "/admin")
    public String adminStartPage(Model model){
        model.addAttribute("users", userServices.getListUser());
        return "admin_start_page";
    }

    @GetMapping(value = "/admin/{id}")
    public String startPage(Model model, @PathVariable("id") Long id) {

        model.addAttribute("users", userServices.getById(id));
        return "admin_show";
    }

    @GetMapping(value = "/admin/new")
    public String newUsers (Model model) {

        model.addAttribute("users", new User());
        return "new";
    }

    @PostMapping(value = "/admin/create")
    public String create(@ModelAttribute("users") User user){
        userServices.add(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/{id}/edit")
    public String edit (Model model, @PathVariable("id") Long id) {
        model.addAttribute("users", userServices.getById(id));
        return "edit";
    }

    @PatchMapping("/admin/{id}/edit")
    public String update(@ModelAttribute("users") User user, @PathVariable("id") Long id) {
        userServices.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userServices.delete(id);
        return "redirect:/admin";
    }





}
