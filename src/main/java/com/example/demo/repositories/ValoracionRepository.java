package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Producto;
import com.example.demo.domain.Usuario;
import com.example.demo.domain.Valoracion;

public interface ValoracionRepository extends JpaRepository <Valoracion,Long> {
    List<Valoracion> findByUsuario(Usuario usuario);
    List<Valoracion> findByProducto(Producto producto);
    Valoracion findByUsuarioAndProducto(Usuario usuario, Producto producto);
}
