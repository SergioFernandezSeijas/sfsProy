package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PuntoAdopcion {

    private String provincia;
    private String nombre;
    private String ubicacion;
    private String contacto;
    private String horario;
    private String url;

    public PuntoAdopcion(String provincia, String nombre, String ubicacion, String contacto, String horario, String url) {
        this.provincia = provincia;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.contacto = contacto;
        this.horario = horario;
        this.url = url;
    }

    public PuntoAdopcion() {

    }

    @Override
    public String toString() {
        return "PuntoAdopcion [provincia=" + provincia + ", nombre=" + nombre + ", ubicacion=" + ubicacion + ", contacto=" + contacto + ", horario" + horario +", url=" + url + "]";
    }
}

