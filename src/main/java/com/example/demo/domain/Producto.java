package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

@Entity
public class Producto {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String nombre;

    private boolean enOferta;

    private String tipoIva;

    private Double precio;

    private Integer stock;

    // private String rutaImagen1;

    // private String rutaImagen2;

    @ManyToOne
    private Categoria categoria;
}
