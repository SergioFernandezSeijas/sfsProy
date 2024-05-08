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

    // public LineaPedido añadir(LineaPedido lineaPedido) {
    //     LineaPedido linPed = lineaPedidoRepository.save(lineaPedido);
    //     pedidoService.actualizar(lineaPedido.getPedido());
    //     return linPed;
    // }

    public LineaPedido añadir(LineaPedido lineaPedido) {
        Pedido pedidoActual = lineaPedido.getPedido();
        Producto producto = lineaPedido.getProducto();
        Integer cantidad = lineaPedido.getCantidad();
        Long precio = lineaPedido.getPrecio();
        
        LineaPedido lineaExistente = lineaPedidoRepository.findByPedidoAndProducto(pedidoActual, producto);
        if (lineaExistente != null) {
            // Si ya existe una línea de pedido para este producto, actualiza la cantidad y el precio
            lineaExistente.setCantidad(lineaExistente.getCantidad() + cantidad);
            lineaExistente.setPrecio(lineaExistente.getPrecio() + precio);
            lineaPedidoRepository.save(lineaExistente); // Guardar los cambios en la línea de pedido existente
        } else {
            // Si no existe una línea de pedido para este producto, crea una nueva
            lineaPedidoRepository.save(lineaPedido);
        }
        
        // Ahora que las modificaciones en las líneas de pedido se han guardado en la base de datos, actualiza el pedido
        pedidoService.actualizar(pedidoActual);
        
        // Retorna la línea de pedido añadida o actualizada
        return lineaPedido;
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
