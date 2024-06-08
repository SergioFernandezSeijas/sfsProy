package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Raza;
import com.example.demo.domain.Usuario;
import com.example.demo.repositories.RazaRepository;

@Service
public class RazaService {
    @Autowired
    RazaRepository razaRepository;

    private final Integer pageSize = 12;

    public Raza añadir(Raza raza) {
        if (razaRepository.existsByNombre(raza.getNombre())) {
            throw new IllegalArgumentException("El nombre ya está en uso.");
        }
        return razaRepository.save(raza);
    }

    public List<Raza> obtenerTodos() {
        return razaRepository.findAll();
    }

    public Raza obtenerPorNombre(String nombre) {
        return razaRepository.findById(nombre).orElse(null);
    }

    public Raza editar(Raza raza) {
        return razaRepository.save(raza);
    }

    public void borrar(String nombre) {
        razaRepository.deleteById(nombre);
    }

    public List<Raza> getRazasPaginados(Integer pageNum) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by("nombre"));
        Page<Raza> pagedResult = razaRepository.findAll(paging);
        if (pagedResult.hasContent())
            return pagedResult.getContent();
        else
            return null;
    }

    public int getTotalPaginas() {
        Pageable paging = PageRequest.of(0, pageSize, Sort.by("nombre"));
        Page<Raza> pagedResult = razaRepository.findAll(paging);
        return pagedResult.getTotalPages();
    }

    // public String getImageUrl(String razaNombre) {
    //     String[] formatos = { "png", "jpg", "webp" };
    //     for (String formato : formatos) {

    //         String ruta = "/images/razas/" + razaNombre.toLowerCase().replace(' ', '_') + "." + formato;
    //     }
    //     return "/images/razas/default.png";
    // }
}
