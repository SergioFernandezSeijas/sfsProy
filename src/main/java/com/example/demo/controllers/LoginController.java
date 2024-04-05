package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    @GetMapping("/signin")
    public String showSignIn() {
        return "/login/signinView";
    }

    @GetMapping("/signout")
    public String showSignOut() {
        return "/login/signoutView";
    }

    @GetMapping("/login")
    public String showLogIn() {
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String showLogOut() {
        return "redirect:/";
    }
    
}
