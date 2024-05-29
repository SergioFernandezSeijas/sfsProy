package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Pedido;
import com.example.demo.domain.Usuario;
import com.example.demo.repositories.PedidoRepository;
import com.example.demo.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Usuario añadir(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El correo ya está en uso.");
        }
        String passCrypted = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passCrypted);
        try {
            return usuarioRepository.save(usuario);

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // public Usuario editar(Usuario usuario) {
    //     return usuarioRepository.save(usuario);
    // }

    public Usuario editar(Usuario usuario) {
        // Obtener el usuario actual de la base de datos
        Usuario usuarioExistente = obtenerPorId(usuario.getId());
        if (usuarioExistente == null) {
            return null;
        }

        // Actualizar los campos del usuario
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setDomicilio(usuario.getDomicilio());

        // Solo encriptar y actualizar la contraseña si se proporciona una nueva
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            String passCrypted = passwordEncoder.encode(usuario.getPassword());
            usuarioExistente.setPassword(passCrypted);
        }

        return usuarioRepository.save(usuarioExistente);
    }

    public void borrar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<Pedido> pedidoUsuario(Long id) {
        Usuario usuario = obtenerPorId(id);
        return pedidoRepository.findByUsuario(usuario);
    }

    public Usuario obtenerUsuarioConectado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String emailUsuarioConectado = authentication.getName();
            return usuarioRepository.findByEmail(emailUsuarioConectado);
        }
        return null;
    }

    public Usuario obtenerPorEmail(String email) {
        Usuario usuarioOpt = usuarioRepository.findByEmail(email);
        return usuarioOpt;
    }

    // public Usuario editarUsuarioConectado(String email, Usuario usuario) {  
    //     Usuario usuarioExistente = usuarioRepository.findByEmail(email);
    //     if (usuarioExistente == null) {
    //         return null; 
    //     }

    //     usuarioExistente.setNombre(usuario.getNombre());
    //     usuarioExistente.setDomicilio(usuario.getDomicilio());

    //     if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
    //         String passCrypted = passwordEncoder.encode(usuario.getPassword());
    //         usuarioExistente.setPassword(passCrypted);
    //     }

    //     return usuarioRepository.save(usuarioExistente);
    // }

}
