package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="nombre")

@Entity
public class Raza {
    @Id
    private String nombre;

    @Column(name = "ESPERANZAVIDA")
    private Integer esperanzaVida;

    private String tamanho;

    // private String rutaImagen;
}
