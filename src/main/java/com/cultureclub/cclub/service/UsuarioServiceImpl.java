package com.cultureclub.cclub.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cultureclub.cclub.entity.Ciudad;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.exceptions.UsuarioDuplicateException;
import com.cultureclub.cclub.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> getUsuarioById(long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario;
        } else {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
    }

    @Override
    public Usuario createUsuario(UsuarioDTO usuario) throws UsuarioDuplicateException {
        System.out.println("Creando usuario: " + usuario.getEmail());
        Optional<Usuario> oldusuario = usuarioRepository.findByEmail(usuario.getEmail());
        if (oldusuario.isPresent()) {
            throw new UsuarioDuplicateException("El usuario con el email " + usuario.getEmail() + " ya existe.");
        } else {
            Usuario newUsuario = new Usuario();
            newUsuario.setNombre(usuario.getNombre());
            newUsuario.setEmail(usuario.getEmail());
            newUsuario.setPassword(usuario.getPassword());
            newUsuario.setApellidos(usuario.getApellidos());
            newUsuario.setCiudad(Ciudad.valueOf(usuario.getCiudad()));
            newUsuario.setTelefono(usuario.getTelefono());
            newUsuario.setPremium(usuario.getIsPremium());

            return usuarioRepository.save(newUsuario);
        }

    }

}
