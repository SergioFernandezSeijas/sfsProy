package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.LineaPedido;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.Producto;
import com.example.demo.repositories.LineaPedidoRepository;

@Service
public class LineaPedidoService {
    @Autowired
    LineaPedidoRepository lineaPedidoRepository;
    
    @Autowired
    PedidoService pedidoService;

    public LineaPedido añadir(LineaPedido lineaPedido) {
        LineaPedido linPed = lineaPedidoRepository.save(lineaPedido);
        pedidoService.actualizar(lineaPedido.getPedido());
        return linPed;
    }

    public LineaPedido obtenerPorId(Long id) {
        return lineaPedidoRepository.findById(id).orElse(null);
    }

    public void eliminar(LineaPedido lineaPedido) {
        lineaPedidoRepository.delete(lineaPedido);
        pedidoService.actualizar(lineaPedido.getPedido());
    }

    public List<LineaPedido> obtenerPorPedido(Pedido pedido) {
        return lineaPedidoRepository.findByPedido(pedido);
    }

    public List<LineaPedido> obtenerPorProducto(Producto producto) {
        return lineaPedidoRepository.findByProducto(producto);
    }

    public LineaPedido obtenerPorLineaPedido(Pedido pedido, Producto producto) {
        return lineaPedidoRepository.findByPedidoAndProducto(pedido, producto);
    }
}
