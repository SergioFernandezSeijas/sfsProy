package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contacto {
    private String asunto;
    private String email;
    private String motivo;
    private String comentarios;
}
