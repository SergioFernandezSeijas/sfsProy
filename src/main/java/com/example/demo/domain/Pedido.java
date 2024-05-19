package com.example.demo.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "importe")
    private Float importe;

    @Column(name = "comprado")
    private boolean comprado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    public Pedido(LocalDateTime fecha, Float importe, Boolean comprado, Usuario usuario) {
        this.fecha = fecha;
        this.importe = importe;
        this.comprado = comprado;
        this.usuario = usuario;
    }
}
