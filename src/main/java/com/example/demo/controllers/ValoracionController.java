package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Producto;
import com.example.demo.domain.Usuario;
import com.example.demo.domain.Valoracion;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.ProductoService;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.ValoracionService;

import jakarta.validation.Valid;



@RequestMapping("/valoraciones")
@Controller
public class ValoracionController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ProductoService productoService;
    @Autowired
    ValoracionService valoracionService;

    @GetMapping("/usuario/{id}") // lista de productos(valoracion) de un usuario
    public String showProyectsByEmpl(@PathVariable long id, Model model) {
        Usuario u = usuarioService.obtenerPorId(id);
        model.addAttribute("listaValoracion",
                valoracionService.obtenerPorUsuario(u));
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        return "valoracion/usuListView";
    }

    @GetMapping("/producto/{id}") // lista de usuarios(valoracion) de un producto
    public String showEmplbyProyect(@PathVariable long id, Model model) throws NotFoundException {
        Producto p = productoService.obtenerPorId(id);
        model.addAttribute("listaValoracion",
                valoracionService.obtenerPorProducto(p));
        model.addAttribute("producto", productoService.obtenerPorId(id));
        return "valoracion/prodListView";
    }

    @GetMapping("/nuevo/{id}")
    public String showValoracion(@PathVariable Long id, Model model) throws NotFoundException {
        Valoracion valoracion = new Valoracion();
        Producto p = productoService.obtenerPorId(id);
        valoracion.setProducto(p);
        model.addAttribute("valoracionesForm", valoracion);
        model.addAttribute("listaUsuarios", usuarioService.obtenerTodos());
        model.addAttribute("listaProductos", productoService.obtenerTodos());
        return "valoracion/valNewFormView";
    }

    @PostMapping("/nuevo/submit")
    public String showValoracionSubmit(@Valid Valoracion valoracion, BindingResult bindingResult, Model model) {
        if(!bindingResult.hasErrors()) {
            valoracionService.añadir(valoracion);
        }
        return "redirect:/producto/";
    }

    @GetMapping("/borrar/{id}")
    public String showDeleteValoracion(@PathVariable long id) {
        valoracionService.eliminar(valoracionService.obtenerPorId(id));
        return "redirect:/producto/";
    }
    
    
}
