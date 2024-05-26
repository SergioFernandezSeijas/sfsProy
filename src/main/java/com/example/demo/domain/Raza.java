package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of="nombre")

@Entity
@Table(name = "raza")
public class Raza {
    @Id
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "origen")
    private String origen;

    @Column(name = "tamano")
    private String tamano;

    @Column(name = "peso")
    private String peso;

    @Column(name = "esperanza_vida")
    private String esperanzaVida;

    @Column(name = "actividad")
    private String actividad;

    @Column(name = "pelaje")
    private String pelaje;

    @Column(name = "puntuacion")
    @Max(value = 5)
    private Integer puntuacion;

    @Column(name = "url")
    private String url;

    // @Column(name = "ESPERANZAVIDA")
    // private Integer esperanzaVida;

    // private String tamanho;

    // private String rutaImagen;

    public Raza(String nombre, Integer puntuacion, String url) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
        this.url = url;
    }
}
