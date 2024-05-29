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

import com.example.demo.domain.LineaPedido;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.Producto;
import com.example.demo.domain.Usuario;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.PedidoRepository;
import com.example.demo.services.LineaPedidoService;

import com.example.demo.services.PedidoService;
import com.example.demo.services.ProductoService;
import com.example.demo.services.UsuarioService;

import jakarta.validation.Valid;

@RequestMapping("/lineaPedido")
@Controller
public class LineaPedidoController {
    @Autowired
    PedidoService pedidoService;
    @Autowired
    ProductoService productoService;
    @Autowired
    LineaPedidoService lineaPedidoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PedidoRepository pedidoRepository;

    @GetMapping("/pedido") 
    public String showProyectsByEmpl(Model model) {
        Usuario usuarioConec = usuarioService.obtenerUsuarioConectado();
        Pedido ped = pedidoRepository.findByUsuarioAndComprado(usuarioConec, false);
        if (ped != null) {
            model.addAttribute("listaLineaPedido",
                    lineaPedidoService.obtenerPorPedido(ped));
            model.addAttribute("pedido", ped);
            model.addAttribute("anhoActual", "©" + Year.now().getValue());
            return "lineaPedido/pedListView";
        }
        else {
            return "redirect:/producto/";
        }
    }

    @GetMapping("/producto/{id}") 
    public String showEmplbyProyect(@PathVariable long id, Model model) throws NotFoundException {
        Producto pro = productoService.obtenerPorId(id);
        model.addAttribute("listaLineaPedido",
                lineaPedidoService.obtenerPorProducto(pro));
        model.addAttribute("producto", productoService.obtenerPorId(id));
        return "lineaPedido/prodListView";
    }

    @GetMapping("/nuevo")
    public String showLineaPedido(Model model) {
        model.addAttribute("lineaPedidoForm", new LineaPedido());
        model.addAttribute("listaPedidos", pedidoService.obtenerTodos());
        model.addAttribute("listaProductos", productoService.obtenerTodos());
        return "lineaPedido/linPedNewFormView";
    }

    @PostMapping("/nuevo/submit")
    public String showLineaPedidoSubmit(@Valid LineaPedido lineaPedido, Pedido pedido, BindingResult bindingResult, Model model) {
        if(!bindingResult.hasErrors()) {
            lineaPedidoService.añadir(lineaPedido);
        }
        return "redirect:/pedido/"+ pedido.getId();
    }

    @GetMapping("/borrar/{id}")
    public String showDeleteLineaPedido(@PathVariable long id) {
        lineaPedidoService.eliminar(lineaPedidoService.obtenerPorId(id));
        return "redirect:/lineaPedido/pedido";
    }
}
