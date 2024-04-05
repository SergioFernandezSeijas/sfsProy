package com.example.demo.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

@Entity
public class Pedido {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime fecha;

    private Float importe;

    @ManyToOne
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Usuario usuario;
    
    private boolean comprado;
}
