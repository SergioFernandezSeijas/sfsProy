package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Contacto;

public interface ContactoRepository extends JpaRepository <Contacto, Long> {
    
}
