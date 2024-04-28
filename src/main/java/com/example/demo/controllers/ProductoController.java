package com.example.demo.controllers;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Producto;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.ProductoService;


import jakarta.validation.Valid;

@RequestMapping("/producto")
@Controller
public class ProductoController {
    @Autowired
    ProductoService productoService;

    @Autowired
    public CategoriaService categoriaService;

    @GetMapping("/")
    public String getAll(@RequestParam(required = false) Integer op, Model model) {
        model.addAttribute("listaProductos", productoService.obtenerTodos());
        model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
        model.addAttribute("categoriaSeleccionada", 0);
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        if (op != null) {
            switch (op) {
                case 1 -> model.addAttribute("msg", "Producto añadido correctamente");
                case 2 -> model.addAttribute("msg", "Producto a editar no encontrado");
                case 3 -> model.addAttribute("msg", "Producto editado correctamente");
                case 4 -> model.addAttribute("msg", "Producto a borrar no encontrado"); 
            }
        }
        return "producto/listView";
    }

    @GetMapping("/nuevo")
    public String getNew(Model model) {
        model.addAttribute("productoForm", new Producto());
        model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
        return "producto/newFormView";
    }

    @PostMapping("/nuevo/submit")
    public String getNewSubmit(@Valid Producto producto, BindingResult bindingResult, Model model) {
        productoService.añadir(producto);
        return "redirect:/producto/?op=1";
    }

    @GetMapping("/editar/{id}")
    public String getEdit(@PathVariable long id, Model model) {
        Producto producto;

        try {
            producto = productoService.obtenerPorId(id);

        } catch (NotFoundException e) {
            return "redirect:/producto/?op=2";
        }

        model.addAttribute("productoForm", producto);
        model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
        return "producto/editFormView";
    }

    @PostMapping("/editar/submit")
    public String getEditSubmit(@Valid Producto producto, BindingResult bindingResult, Model model) {
        productoService.editar(producto);
        return "redirect:/producto/?op=3";
    }

    @GetMapping("/borrar/{id}")
    public String getDelete(@PathVariable long id, Model model) {
        try {
            productoService.borrar(id);
        } catch (NotFoundException e) {
            return "redirect:/producto/?op=4";
        }
        return "redirect:/producto/";
    }

    @GetMapping("/porCateg/{idCat}")
    public String showListInCategory(@PathVariable Long idCat, Model model) {
        model.addAttribute("listaProductos", productoService.obtenerPorCategoria(idCat));
        model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
        model.addAttribute("categoriaSeleccionada", idCat);
        return "producto/listView";
    }

    @GetMapping("/comprar/{id}")
    public String getComprar(@PathVariable Long id) {
        productoService.comprar(id, 1);
        return "redirect:/producto/?op=1";
    }
    
}
