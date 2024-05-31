package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Respuesta;

public interface RespuestaRepository extends JpaRepository<Respuesta,Long> {
    
}
