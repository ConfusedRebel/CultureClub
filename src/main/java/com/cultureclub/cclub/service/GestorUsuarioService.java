package com.cultureclub.cclub.service;

import java.util.Optional;

import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.exceptions.UsuarioDuplicateException;

public interface GestorUsuarioService {

    public Optional<Usuario> getUsuarioById(long id);

    public Usuario createUsuario(UsuarioDTO usuario) throws UsuarioDuplicateException;

    public Object getUsuario(UsuarioDTO param);

    public Object getPremiumUsuarios();

    public void deleteUsuario(UsuarioDTO param);

    public Object getAllUsuarios();

    public Usuario login(String email, String password);

}
