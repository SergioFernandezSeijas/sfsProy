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
@Table(name = "linea_pedido")
public class LineaPedido {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "pedido_id")
   // @OnDelete(action = OnDeleteAction.CASCADE)
   private Pedido pedido;

   @ManyToOne
   @JoinColumn(name = "producto_id")
   // @OnDelete(action = OnDeleteAction.CASCADE)
   private Producto producto;

   @Column(name = "precio")
   private Long precio;

   @Column(name = "cantidad")
   private Integer cantidad;

   public LineaPedido(Pedido pedido, Producto producto, Long precio, Integer cantidad) {
        this.pedido = pedido;
        this.producto = producto;
        this.precio = precio;
        this.cantidad = cantidad;
    }
}
