package com.example.demo.controllers;

import jakarta.validation.Valid;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.example.demo.domain.Producto;
import com.example.demo.domain.Usuario;
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
        List<Categoria> categorias = categoriaService.obtenerTodos();
        // Creo un nuevo mapa que relacionará los ID de las categorías con las listas de productos
        Map<Long, List<Producto>> productosPorCategoria = new HashMap<>();

        for (Categoria categoria : categorias) {
            List<Producto> productos = productoService.obtenerPorCategoria(categoria.getId());
            //Cuando se obtienen los productos para una categoría determinada, se agregan al mapa
            productosPorCategoria.put(categoria.getId(), productos);
        }

        model.addAttribute("productosPorCategoria", productosPorCategoria);
        model.addAttribute("listaCategorias", categorias);
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "category/categoryListView";
    }

    @GetMapping("/new")
    public String showNew(Model model) {
        model.addAttribute("categoriaForm", new Categoria());
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "category/categoryNewView";
    }

    @PostMapping("/new/submit")
    public String showNewSubmit(
            @Valid @ModelAttribute("categoriaForm") Categoria nuevaCategoria,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoriaForm", nuevaCategoria);
            model.addAttribute("anhoActual", "©" + Year.now().getValue());
            model.addAttribute("error", "Errores en el formulario");
            return "category/categoryNewView";
        }
        try {
            categoriaService.añadir(nuevaCategoria);
        } catch (IllegalArgumentException e) {
            model.addAttribute("categoriaForm", nuevaCategoria);
            model.addAttribute("anhoActual", "©" + Year.now().getValue());
            model.addAttribute("error", e.getMessage());
            return "category/categoryNewView";
        }
        return "redirect:/categorias/";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Categoria categoria = categoriaService.obtenerPorId(id);
        if (categoria != null) {
            model.addAttribute("categoriaForm", categoria);
            model.addAttribute("anhoActual", "©" + Year.now().getValue());
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
