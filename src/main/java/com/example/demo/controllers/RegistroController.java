package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Rol;
import com.example.demo.domain.Usuario;
import com.example.demo.services.UsuarioService;

import jakarta.validation.Valid;


@RequestMapping("/registro")
@Controller
public class RegistroController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("registroForm", new Usuario());
        return "registro/newFormView";
    }

    @PostMapping("/nuevo/submit")  
    public String nuevo(@Valid Usuario usuario, BindingResult bindingResult, Model model) {
        usuario.setRol(Rol.USUARIO);
        usuarioService.añadir(usuario);
        return "redirect:/usuario/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable long id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario != null) {
            model.addAttribute("usuarioForm", usuario);
            return "usuario/editFormView";
        } else {
            return "redirect:/usuario/";
        }
    }

    @PostMapping("/editar/submit")
    public String getEditSubmit(@Valid Usuario usuario, BindingResult bindingResult, Model model) {
        usuarioService.editar(usuario);
        return "redirect:/usuario/";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable long id, Model model) {
        usuarioService.borrar(id);
        return "redirect:/usuario/";
    }
}
