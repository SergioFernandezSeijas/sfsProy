package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.LineaPedido;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.Producto;
import com.example.demo.domain.Usuario;
import com.example.demo.repositories.LineaPedidoRepository;
import com.example.demo.repositories.PedidoRepository;
import com.example.demo.repositories.ProductoRepository;
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

    @Autowired
    ProductoRepository productoRepository;

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

    // public void cerrar(Pedido pedido) {
    //     pedido.setComprado(true);
    //     pedidoRepository.save(pedido);
    // }

    @Transactional
    public void cerrar(Pedido pedido) {
        List<LineaPedido> lineasPedido = lineaPedidoRepository.findByPedido(pedido);

        // Verificar stock
        for (LineaPedido lp : lineasPedido) {
            Producto producto = lp.getProducto();
            if (producto.getStock() < lp.getCantidad()) {
                throw new IllegalArgumentException("No hay suficiente stock para el producto: " + producto.getNombre());
            }
        }

        // Reducir stock
        for (LineaPedido lp : lineasPedido) {
            Producto producto = lp.getProducto();
            producto.setStock(producto.getStock() - lp.getCantidad());
            System.out.println("--------Stock----------------");
            System.out.println(producto.getStock());
            // Guardar el producto con el stock actualizado
            productoRepository.save(producto);
        }
        pedido.setComprado(true);
        pedidoRepository.save(pedido);
    }
}
