package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Producto;
import com.example.demo.domain.Usuario;
import com.example.demo.domain.Valoracion;
import com.example.demo.repositories.ValoracionRepository;

@Service
public class ValoracionService {
    @Autowired
    ValoracionRepository valoracionRepository;

    @Autowired
    UsuarioService usuarioService;

    public Valoracion añadir(Valoracion valoracion) {
        Usuario usuarioConectado = usuarioService.obtenerUsuarioConectado();
        valoracion.setUsuario(usuarioConectado);
        return valoracionRepository.save(valoracion);
    }

    public Valoracion obtenerPorId(Long id) {
        return valoracionRepository.findById(id).orElse(null);
    }

    public void eliminar(Valoracion valoracion) {
        Usuario usuarioConectado = usuarioService.obtenerUsuarioConectado();
        if (usuarioConectado == valoracion.getUsuario()) {
            valoracionRepository.delete(valoracion);
        }
    }

    public List<Valoracion> obtenerPorUsuario(Usuario usuario) {
        return valoracionRepository.findByUsuario(usuario);
    }

    public List<Valoracion> obtenerPorProducto(Producto producto) {
        return valoracionRepository.findByProducto(producto);
    }

    public Valoracion obtenerPorValoracion(Usuario usuario, Producto producto) {
        return valoracionRepository.findByUsuarioAndProducto(usuario, producto);
    }
}
