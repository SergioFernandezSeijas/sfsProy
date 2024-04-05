package com.example.demo.domain;

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
public class LineaPedido {
   @Id
   @GeneratedValue
   private Long id;

   @ManyToOne
   @OnDelete(action = OnDeleteAction.CASCADE)
   private Pedido pedido;

   @ManyToOne
   @OnDelete(action = OnDeleteAction.CASCADE)
   private Producto producto;

   private Long precio;
   private Integer cantidad;
}
