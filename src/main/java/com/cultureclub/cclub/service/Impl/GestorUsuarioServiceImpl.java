package com.cultureclub.cclub.service.Impl;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.enumeradores.Rol;
import com.cultureclub.cclub.mapper.UsuarioMapper;
import com.cultureclub.cclub.repository.UsuarioRepository;
import com.cultureclub.cclub.service.Int.GestorUsuarioService;

@Service
class GestorUsuarioServiceImpl implements GestorUsuarioService {

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
    public Object getUsuario(UsuarioDTO param) {

        if (param.getIdUsuario() != null && param.getEmail() == null) {
            Optional<Usuario> usuario = usuarioRepository.findById(param.getIdUsuario());
            return usuario.orElseThrow(
                    () -> new IllegalArgumentException("Usuario no encontrado con ID: " + param.getIdUsuario()));
        }
        if (param.getEmail() != null && param.getIdUsuario() == null) {
            Optional<Usuario> usuario = usuarioRepository.findByEmail(param.getEmail());
            return usuario.orElseThrow(
                    () -> new IllegalArgumentException("Usuario no encontrado con ID: " + param.getIdUsuario()));
        }
        throw new IllegalArgumentException("Debe proporcionar un ID o un email para buscar el usuario.");
    }

    @Override
    public Object getPremiumUsuarios(boolean isPremium) {
        if (isPremium) {
            System.out.println("Obteniendo usuarios premium");
            return usuarioRepository.findByRolesContaining(Rol.PREMIUM);
        } else {
            System.out.println("Obteniendo usuarios no premium");
            return usuarioRepository.findByRolesNotContaining(Rol.PREMIUM);
        }

    }

    @Override
    public Object getAdminUsuarios(boolean isAdmin) {
        if (isAdmin) {
            System.out.println("Obteniendo usuarios administradores");
            return usuarioRepository.findByRolesContaining(Rol.ADMINISTRADOR);
        } else {
            System.out.println("Obteniendo usuarios no administradores");
            return usuarioRepository.findByRolesNotContaining(Rol.ADMINISTRADOR);
        }

    }

    @Override
    public void deleteUsuario(UsuarioDTO param) {
        Optional<Usuario> usuario = Optional.empty();

        if (param.getIdUsuario() != null) {
            usuario = usuarioRepository.findById(param.getIdUsuario());
        } else if (param.getEmail() != null) {
            usuario = usuarioRepository.findByEmail(param.getEmail());
        }

        if (usuario.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + param.getIdUsuario());
        }
        usuarioRepository.deleteById(usuario.get().getIdUsuario());
    }

    @Override
    public Object getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public URI createUsuario(UsuarioDTO param) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(param.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + param.getEmail());
        }
        Usuario nuevoUsuario = UsuarioMapper.toEntity(param);
        nuevoUsuario.setIdUsuario(null); // Asegurarse de que el ID sea nulo para que se genere uno nuevo
        usuarioRepository.save(nuevoUsuario);
        return URI.create("/usuarios/" + nuevoUsuario.getIdUsuario());
    }

    @Override
    public Usuario updateUsuarioRol(UsuarioDTO param) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(param.getIdUsuario());
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + param.getIdUsuario());
        }
        Usuario usuario = usuarioOpt.get();
        if (param.getRoles() != null && !param.getRoles().isEmpty()) {
            usuario.setRoles(param.getRoles());
        } else {
            throw new IllegalArgumentException("Debe proporcionar al menos un rol para actualizar el usuario.");
        }
        return usuarioRepository.save(usuario);
    }

}