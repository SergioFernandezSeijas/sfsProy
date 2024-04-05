package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Categoria;
import com.example.demo.repositories.CategoriaRepository;



@Service
public class CategoriaService {

    @Autowired
    public ProductoService productoService;

    @Autowired
    CategoriaRepository categoriaRepository;

    public Categoria añadir(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> obtenerTodos() {
        return categoriaRepository.findAll();
    }

    public Categoria obtenerPorId(long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria editar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void borrar(Long id) {
        categoriaRepository.deleteById(id);
    }
}
