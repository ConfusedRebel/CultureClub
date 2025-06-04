package com.cultureclub.cclub.service;

import java.util.Optional;

import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.exceptions.UsuarioDuplicateException;

public interface UsuarioService {

    public Optional<Usuario> getUsuarioById(long id);

    public Usuario createUsuario(UsuarioDTO usuario) throws UsuarioDuplicateException;
}
