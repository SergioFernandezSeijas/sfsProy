package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Usuario;
import com.example.demo.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Long>{
    List<Pedido> findByUsuario(Usuario usuario);

    Pedido findByUsuarioAndComprado(Usuario usuario, Boolean comprado);
}
