package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Pregunta;

public interface PreguntaRepository extends JpaRepository<Pregunta,Long> {
    
}
