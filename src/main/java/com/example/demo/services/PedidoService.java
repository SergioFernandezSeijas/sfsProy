package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.LineaPedido;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.Usuario;
import com.example.demo.repositories.LineaPedidoRepository;
import com.example.demo.repositories.PedidoRepository;
import com.example.demo.repositories.UsuarioRepository;

@Service
public class PedidoService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    LineaPedidoRepository lineaPedidoRepository;

    @Autowired
    UsuarioService usuarioService;

    public Pedido añadir(Pedido pedido) {
        Usuario usuarioConectado = usuarioService.obtenerUsuarioConectado();
        pedido.setUsuario(usuarioConectado);
        pedido.setFecha(LocalDateTime.now());
        return pedidoRepository.save(pedido);
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    public void actualizar(Pedido pedido) {
        List<LineaPedido> linPed = lineaPedidoRepository.findByPedido(pedido);
        float importe = 0;
        for (LineaPedido lp : linPed) {
            importe += lp.getProducto().getPrecio() * lp.getCantidad();
        }
        pedido.setImporte(importe);
        System.out.println("--------------------");
        System.out.println(pedido.getImporte());
        System.out.println("--------------------");
        pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerPorUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            return pedidoRepository.findByUsuario(usuario);
        }
        else {
            return null;
        }
    }

    public void borrar(Long id) {
        pedidoRepository.deleteById(id);
    }

    public void cerrar(Pedido pedido) {
        pedido.setComprado(true);
        pedidoRepository.save(pedido);
    }
}
