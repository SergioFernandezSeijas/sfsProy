package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Raza;
import java.util.List;


public interface RazaRepository extends JpaRepository<Raza,String>{
    // List<Raza> findByPuntuacion(Integer puntuacion);
}
