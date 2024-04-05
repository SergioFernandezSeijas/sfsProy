package com.example.demo.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Categoria;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.ProductoService;



@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    public CategoriaService categoriaService;

    @Autowired
    ProductoService productoService;

    @GetMapping({ "/", "/list", "" })
    public String showList(Model model) {
        model.addAttribute("listaCategorias", categoriaService.obtenerTodos());
        return "category/categoryListView";
    }

    @GetMapping("/new")
    public String showNew(Model model) {
        model.addAttribute("categoriaForm", new Categoria());
        return "category/categoryNewView";
    }

    @PostMapping("/new/submit")
    public String showNewSubmit(
            @Valid @ModelAttribute("categoriaForm") Categoria nuevaCategoria,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "category/categoryNewView";
        categoriaService.añadir(nuevaCategoria);
        return "redirect:/categorias/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Categoria categoria = categoriaService.obtenerPorId(id);
        if (categoria != null) {
            model.addAttribute("categoriaForm", categoria);
            return "category/categoryEditView";
        } else {
            return "redirect:/categorias/";
        }
    }

    @PostMapping("/edit/submit")
    public String showEditSubmit(@Valid @ModelAttribute("categoriaForm") Categoria categoria,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "category/categoryEditView";
        } else {
            categoriaService.editar(categoria);
            return "redirect:/categorias/";
        }
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id) {
        if (productoService.obtenerPorCategoria(id).size() == 0) {
            categoriaService.borrar(id);
        }
        return "redirect:/categorias/";
    }
}
