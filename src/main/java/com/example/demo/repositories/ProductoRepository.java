package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    List<Producto> findByCategoria(Categoria categoria);
}
