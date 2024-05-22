package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Contacto;
import com.example.demo.repositories.ContactoRepository;

@Service
public class ContactoService {

    @Autowired
    ContactoRepository contactoRepository;

    public Contacto añadir(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

    public List<Contacto> obtenerTodos() {
        return contactoRepository.findAll();
    }

    public Contacto obtenerPorId(long id) {
        return contactoRepository.findById(id).orElse(null);
    }

    public Contacto editar(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

    public void borrar(Long id) {
        contactoRepository.deleteById(id);
    }
}
