package com.example.demo.controllers;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.demo.domain.Raza;
import com.example.demo.services.RazaService;

import jakarta.validation.Valid;

@RequestMapping("/razas")
@Controller
public class RazaController {
    @Autowired
    RazaService razaService;

    @GetMapping("/")
    public String showList(@RequestParam(required = false) Integer pag, Model model) {
        int ultPag = razaService.getTotalPaginas() - 1;
        if (pag == null || pag < 0 || pag > ultPag) pag = 0;
        Integer pagSig = ultPag > pag ? pag + 1 : ultPag;
        Integer pagAnt = pag > 0 ? pag - 1 : 0;
        model.addAttribute("listaRazas", razaService.getRazasPaginados(pag));
        model.addAttribute("pagSiguiente", pagSig);
        model.addAttribute("pagAnterior", pagAnt);
        model.addAttribute("pagFinal", ultPag);
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "raza/listView";
    }

     @GetMapping("/nuevo")
    public String getNew(Model model) {
        model.addAttribute("raza", new Raza());
        return "raza/newFormView";
    }

    @PostMapping("/nuevo/submit")
    public String getNewSubmit(@Valid Raza raza, BindingResult bindingResult, Model model) {
        razaService.añadir(raza);
        return "redirect:/razas/?op=1";
    }

    @GetMapping("/editar/{nombre}")
    public String getEdit(@PathVariable String nombre, Model model) {
        Raza raza = razaService.obtenerPorNombre(nombre);
        model.addAttribute("raza", raza);
        return "raza/editFormView";
    }

    @PostMapping("/editar/submit")
    public String getEditSubmit(@Valid Raza raza, BindingResult bindingResult, Model model) {
        razaService.editar(raza);
        return "redirect:/razas/?op=3";
    }

    @GetMapping("/borrar/{nombre}")
    public String getDelete(@PathVariable String nombre, Model model) {
        razaService.borrar(nombre);
        return "redirect:/razas/";
    }

    // public String getImageUrl(String razaNombre) {
    //     String[] formatos = {"png", "jpg", "webp"};
    //     for (String formato : formatos) {
    //         String ruta = "/images/razas/" + razaNombre.toLowerCase().replace(' ', '_') + "." + formato;
    //     }
    //     return "/images/razas/default.png"; // Por ejemplo, una imagen de fallback llamada default.png
    // }
    
}
