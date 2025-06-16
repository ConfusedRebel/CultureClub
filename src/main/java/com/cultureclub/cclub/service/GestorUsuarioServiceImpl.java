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
    public Object getPremiumUsuarios() {
        return usuarioRepository.findByPremium(true);
    }

    @Override
    public void deleteUsuario(UsuarioDTO param) {
        Optional<Usuario> usuario = usuarioRepository.findById(param.getIdUsuario());
        if (usuario.isEmpty() && param.getEmail() != null) {
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

}