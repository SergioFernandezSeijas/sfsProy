package com.example.demo.controllers;

import java.time.Year;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class MainController {
    @GetMapping("/") 
        public String showHome(Model model) {
            model.addAttribute("anhoActual", "©" + Year.now().getValue());
            return "main/homeView";
        }


    @GetMapping("/aboutUs")
    public String showIndex() {
        return "main/aboutUsView";
    } 
    
    // @GetMapping ("/usuario") 
    //     public String menu (@RequestParam (name = "user", required = false) String nombreUsuario, Model model) {
    //         if (nombreUsuario == null) {
    //             nombreUsuario = "";
    //         }
    //         model.addAttribute("usuario", nombreUsuario);
    //         return "main/homeView";
    //     }
    
    @GetMapping("/historia")
    public String getHistoria(Model model) {
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "main/historiaView";
    }

    @GetMapping("/motivo")
    public String getMethodName(Model model) {
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "main/motivoView";
    }

    @GetMapping("/veterinario")
    public String getVeterinario(Model model) {
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "main/veterinarioView";
    }

    @GetMapping("/ayuda")
    public String getAyuda(Model model) {
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "main/ayudaView";
    }

    @GetMapping("/terminosCondiciones")
    public String getTerminosCondiciones(Model model) {
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "main/terminosCondicionesView";
    }
}
