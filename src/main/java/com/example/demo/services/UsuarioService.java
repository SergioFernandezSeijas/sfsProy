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

    public Usuario editar(Usuario usuario) {
        return usuarioRepository.save(usuario);
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
}
