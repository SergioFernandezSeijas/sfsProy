package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.LineaPedido;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.Producto;


public interface LineaPedidoRepository extends JpaRepository<LineaPedido,Long> {
    List<LineaPedido> findByPedido(Pedido pedido);
    List<LineaPedido> findByProducto(Producto producto);
    LineaPedido findByPedidoAndProducto(Pedido pedido, Producto producto);
} 
