package com.cultureclub.cclub.service.Int;

import java.net.URI;
import java.util.Optional;

import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;

public interface GestorUsuarioService {

    public Optional<Usuario> getUsuarioById(long id);

    public Object getUsuario(UsuarioDTO param);

    public Object getPremiumUsuarios(boolean isPremium);

    public Object getAdminUsuarios(boolean isAdmin);

    public void deleteUsuario(UsuarioDTO param);

    public Object getAllUsuarios();

    public URI createUsuario(UsuarioDTO param);

}
