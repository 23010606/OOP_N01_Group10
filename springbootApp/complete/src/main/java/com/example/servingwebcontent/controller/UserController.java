package com.example.servingwebcontent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.servingwebcontent.model.User;
import com.example.servingwebcontent.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, 
                             @RequestParam String password,
                             @RequestParam String email,
                             Model model) {
        User user = new User(username, password, email);
        User registeredUser = userService.registerUser(user);
        
        if (registeredUser == null) {
            model.addAttribute("error", "Username already exists");
            return "auth/register";
        }
        
        return "redirect:/login?registered=true";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        User user = userService.loginUser(username, password);
        
        if (user == null) {
            model.addAttribute("error", "Invalid username or password");
            return "auth/login";
        }
        
        session.setAttribute("user", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("message", "You have been successfully logged out");
        return "redirect:/login?logged_out=true";
    }
}