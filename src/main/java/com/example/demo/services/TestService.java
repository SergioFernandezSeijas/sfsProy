package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Pregunta;
import com.example.demo.domain.Raza;
import com.example.demo.domain.Respuesta;
import com.example.demo.repositories.PreguntaRepository;
import com.example.demo.repositories.RazaRepository;
import com.example.demo.repositories.RespuestaRepository;

@Service
public class TestService {
    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private RazaRepository razaRepository;

    public List<Pregunta> obtenerPreguntas() {
        return preguntaRepository.findAll();
    }

 //   Este método coge todos los id de las respuestas seleccionadas, asegurándose con el optional que estén presentes en la bd, y luego suma la puntuación de cada respuesta
    public Integer calcularPuntuacion(List<Long> respuestasIds) {
        return respuestasIds.stream()
                .map(respuestaRepository::findById)
                .filter(Optional::isPresent)
                .mapToInt(respuesta -> respuesta.get().getValor())
                .sum();
    }

    // //Suponemos que las respuestas se encuentran en la bd
    // public int calcularPuntuacion(List<Long> respuestasIds) {
    //     return respuestaRepository.findAllById(respuestasIds).stream()
    //             .mapToInt(Respuesta::getValor)
    //             .sum();
    // }

    public List<Raza> obtenerRazasRecomendadas(int puntuacion) {
        return razaRepository.findAll().stream()
                .filter(raza -> raza.getPuntuacion() >= puntuacion - 2 && raza.getPuntuacion() <= puntuacion + 2)
                .collect(Collectors.toList());
    }

    // public List<Pregunta> obtenerPreguntas() {
    //     return preguntaRepository.findAll();
    // }

    // public List<Raza> buscarRazasPorPuntuacion(int puntuacion) {
    //     List<Raza> razas = razaRepository.findByPuntuacion(puntuacion);
    //     return razas.stream()
    //             .filter(raza -> raza.getPuntuacion() == puntuacion)
    //             .collect(Collectors.toList());
    // }

    // public Respuesta obtenerRespuestaPorId(long respuestaId) {
    //     return respuestaRepository.findById(respuestaId).orElse(null);
    // }
}
