package com.example.demo.controllers;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Contacto;
import com.example.demo.exceptions.SpamException;
import com.example.demo.services.ContactoService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/contacto")
public class ContactoController {
    @Autowired
    ContactoService contactoService;

    @GetMapping("/")
    public String showContact(Model model) { 
        model.addAttribute("formContacto", new Contacto());
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "contacto/formularioView";
    }  

    @PostMapping("/submit")
    public String calcularContacto(@Valid Contacto formContacto, BindingResult bindingResult, 
                                @RequestParam(name = "input_apellidos", required = false) String apellidos, 
                                Model model, HttpServletResponse response) {

        if (apellidos != null && !apellidos.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new SpamException("Acceso prohibido debido a detección de spam.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("anhoActual", "©" + Year.now().getValue());
            return "contacto/formularioView";
        }
        contactoService.añadir(formContacto);
        model.addAttribute("mensajeExitoso", "Formulario enviado correctamente");
        model.addAttribute("formContacto", new Contacto());
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "redirect:/";
    }

    @ExceptionHandler(SpamException.class)
    public String deteccionSpam(SpamException ex, Model model) {
        model.addAttribute("mensajeError", ex.getMessage());
        return "contacto/error403";
    }

    @GetMapping("/comentarios")
    public String mostrarMensajes(Model model) {
        model.addAttribute("mensajes", contactoService.obtenerTodos());
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "contacto/comentarioView";
    }
}
