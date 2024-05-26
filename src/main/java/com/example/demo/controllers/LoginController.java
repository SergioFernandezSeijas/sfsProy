package com.example.demo.controllers;

import java.time.Year;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    @GetMapping("/signin")
    public String showSignIn(Model model) {
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
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
