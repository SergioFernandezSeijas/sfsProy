package com.example.demo.controllers;

import java.time.Year;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Contacto;

import jakarta.validation.Valid;

@RequestMapping("/contacto")
@Controller
public class ContactoController {
    @GetMapping("/")
    public String showContact(Model model) { 
        model.addAttribute("formContacto", new Contacto());
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "contacto/formularioView";
    }  

    @PostMapping("/submit")
    public String calcularContacto(@Valid Contacto formContacto, BindingResult bindingResult, Model model) {
        model.addAttribute("contacto", formContacto);
        return "contacto/formularioResultView";
    }
}
