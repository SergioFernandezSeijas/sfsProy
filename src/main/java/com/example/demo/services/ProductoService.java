package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Producto;
import com.example.demo.exceptions.NotFoundException;

public interface ProductoService {
    Producto añadir(Producto producto);
    List<Producto> obtenerTodos();
    Producto obtenerPorId(long id) throws NotFoundException;
    Producto editar(Producto producto);
    void borrar(Long id) throws NotFoundException;

    List<Producto> obtenerPorCategoria(Long idCategoria);

    void comprar(Long idProd, Integer cantidad);
}
