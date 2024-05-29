package com.example.demo.controllers;

import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Pedido;
import com.example.demo.domain.Usuario;
import com.example.demo.repositories.PedidoRepository;
import com.example.demo.services.PedidoService;
import com.example.demo.services.UsuarioService;

import jakarta.validation.Valid;


@RequestMapping("/pedido")
@Controller
public class PedidoController {
    @Autowired
    PedidoService pedidoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PedidoRepository pedidoRepository;

    @GetMapping("/{id}")
    public String mostrar(@PathVariable Long id, Model model) {
        model.addAttribute("listarPedidos", pedidoService.obtenerPorUsuario(id));
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        return "pedido/listView";
    }

    @GetMapping("/")
    public String getAll(Model model) {
        Usuario usuarioConec = usuarioService.obtenerUsuarioConectado();
        List<Pedido> listaPedidos = pedidoRepository.findByUsuario(usuarioConec);
        model.addAttribute("listarPedidos", listaPedidos);
        model.addAttribute("usuario", usuarioConec);
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "pedido/listView";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pedidosForm", new Pedido());
        model.addAttribute("listaUsuarios", usuarioService.obtenerTodos());
        return "pedido/newFormView";
    }

    @PostMapping("/nuevo/submit")  
    public String nuevo(@Valid Pedido pedido, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "pedido/newFormView";
        }
        pedidoService.añadir(pedido);
        return "redirect:/lineaPedido/pedido/" + pedido.getId();
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id, Model model) {
        pedidoService.borrar(id);
        return "redirect:/usuario/";
    }

//    @GetMapping("/cerrar")
//    public String getCerrar() {
//        Usuario usuarioConec = usuarioService.obtenerUsuarioConectado();
//        Pedido ped = pedidoRepository.findByUsuarioAndComprado(usuarioConec, false);
//        pedidoService.cerrar(ped);
//        return "redirect:/producto/";
//    }


    @GetMapping("/cerrar")
    public String getCerrar(RedirectAttributes redirectAttributes) {
        //RedirectAttributes, lo utilizo para mostrar mensajes de confirmación o error cuando se cierre el pedido
        Usuario usuarioConectado = usuarioService.obtenerUsuarioConectado();
        Pedido pedido = pedidoRepository.findByUsuarioAndComprado(usuarioConectado, false);
        
        if (pedido != null) {
            try {
                pedidoService.cerrar(pedido);
                redirectAttributes.addFlashAttribute("msg", "Pedido cerrado exitosamente.");
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
            }
        }
        
        return "redirect:/producto/";
    }
    
}
