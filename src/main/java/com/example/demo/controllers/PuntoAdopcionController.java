package com.example.demo.controllers;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.example.demo.domain.PuntoAdopcion;
import com.example.demo.services.PuntoAdopcionService;

@Controller
@RequestMapping("/adopta")
public class PuntoAdopcionController {
    @Autowired
    private PuntoAdopcionService puntoAdopcionService;

    @GetMapping("/")
    public String showList(Model model) {
        model.addAttribute("formInfo", new PuntoAdopcion());
        model.addAttribute("listaAdopta", puntoAdopcionService.getLugaresAdopcion());
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "puntoAdopcion/adoptaView";
    }

    @PostMapping("/")
    public String showNewSubmit(PuntoAdopcion formInfo, Model model) {
        if (formInfo.getProvincia().equals("")) {
            return "redirect:/adopta/";
        }
        model.addAttribute("formInfo", new PuntoAdopcion());
        model.addAttribute("lugarSeleccionado", puntoAdopcionService.getAdopta(formInfo.getProvincia()));
        model.addAttribute("listaAdopta", puntoAdopcionService.getLugaresAdopcion());
        return "puntoAdopcion/adoptaView";
    }
}
