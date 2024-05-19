package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.LineaPedido;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.Producto;
import com.example.demo.domain.Usuario;
import com.example.demo.repositories.CategoriaRepository;
import com.example.demo.repositories.PedidoRepository;
import com.example.demo.repositories.ProductoRepository;

@Service
public class ProductoServicelmplMem implements ProductoService {
    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    PedidoService pedidoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    LineaPedidoService lineaPedidoService;

    public Producto añadir(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto editar(Producto producto) {
        return productoRepository.save(producto);
    }

    public void borrar(Long id) {
        productoRepository.deleteById(id);
    }
    
    public List<Producto> obtenerPorCategoria(Long idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria).orElse(null);
        if (categoria != null) {
            return productoRepository.findByCategoria(categoria);
        }
        return null;
    }

    public void comprar(Long idProd, Integer cantidad) {
        Usuario usuarioConec = usuarioService.obtenerUsuarioConectado();
        Pedido pedidoActual = pedidoRepository.findByUsuarioAndComprado(usuarioConec, false);
        if (pedidoActual == null) {
            pedidoActual = pedidoService.añadir(new Pedido(null, null, 0f, false, usuarioConec));
        }
        Producto producto = this.obtenerPorId(idProd);
        LineaPedido lineaPedido = new LineaPedido(null, pedidoActual, producto,(long)(producto.getPrecio() * cantidad), cantidad);
        lineaPedidoService.añadir(lineaPedido);
    }
}
