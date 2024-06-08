package com.example.demo.controllers;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Rol;
import com.example.demo.domain.Usuario;
import com.example.demo.services.UsuarioService;

import jakarta.validation.Valid;


@RequestMapping("/usuario")
@Controller
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/")
    public String mostrar(Model model) {
        model.addAttribute("listaUsuarios", usuarioService.obtenerTodos());
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "usuario/listView";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuarioForm", new Usuario());
        return "usuario/newFormView";
    }

    // @PostMapping("/nuevo/submit")  
    // public String nuevo(@Valid Usuario usuario, BindingResult bindingResult, Model model) {
    //     usuarioService.añadir(usuario);
    //     return "redirect:/usuario/";
    // }
    @PostMapping("/nuevo/submit")
    public String nuevo(@Valid Usuario usuario, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("usuarioForm", usuario);
            model.addAttribute("error", "Errores en el formulario");
            return "usuario/newFormView";
        }
        try {
            usuarioService.añadir(usuario);
        } catch (IllegalArgumentException e) {
            model.addAttribute("usuarioForm", usuario);
            model.addAttribute("error", e.getMessage());
            return "usuario/newFormView";
        }
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
        if (bindingResult.hasErrors()) {
            return "usuario/editFormView";
        }
        usuarioService.editar(usuario);
        return "redirect:/usuario/";
    }

    @ModelAttribute
    public void addAttributes(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Usuario usuarioConectado = usuarioService.obtenerPorEmail(userDetails.getUsername());
            model.addAttribute("usuarioConectado", usuarioConectado);
        }
    }

    @GetMapping("/perfil/editar")
    public String editarPerfil(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Usuario usuarioConectado = usuarioService.obtenerPorEmail(userDetails.getUsername());
            model.addAttribute("usuarioForm", usuarioConectado);
            return "usuario/editarPerfilView";
        }
        return "redirect:/login";
    }

    @PostMapping("/perfil/editar/submit")
    public String editarPerfilSubmit(@Valid @ModelAttribute("usuarioForm") Usuario usuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "usuario/editarPerfilView";
        }
        usuarioService.editar(usuario);
        return "redirect:/";
    }


    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable long id, Model model) {
        usuarioService.borrar(id);
        return "redirect:/usuario/";
    }
}


    // @GetMapping("/editarPerfil")
    // public String editProfile(Model model) {
    //     Usuario usuario = usuarioService.obtenerUsuarioConectado();

    //     if (usuario != null) {
    //         model.addAttribute("usuarioForm", usuario);
    //         System.out.println("--------------------------------");
    //         System.out.println(usuario);
    //         return "usuario/editarUsuarioConectado";
    //     } else {
    //         System.out.println("--------------------------");
    //         System.out.println("FALLA OASOAOAFOA");
    //         return "redirect:/producto/";
    //     }
    // }


// @PostMapping("/editarPerfil/submit")
    // public String getEditarSubmit(@Valid Usuario usuario, BindingResult bindingResult, Model model) {
    //     usuarioService.editarUsuarioConectado(usuario);
    //     return "redirect:/producto/";
    // }