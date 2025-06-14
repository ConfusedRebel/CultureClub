package com.mapper;

import com.cultureclub.cclub.entity.Ciudad;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellidos(usuario.getApellidos());
        dto.setCiudad(usuario.getCiudad().toString());
        dto.setEmail(usuario.getEmail());
        dto.setPuntuacion(usuario.getPuntuacion());
        dto.setTelefono(usuario.getTelefono());
        return dto;
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(dto.getIdUsuario());
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        // Assuming Ciudad is an enum and dto.getCiudad() is a String
        if (dto.getCiudad() != null) {
            usuario.setCiudad(Ciudad.valueOf(dto.getCiudad()));
        }
        usuario.setEmail(dto.getEmail());
        usuario.setPuntuacion(dto.getPuntuacion());
        usuario.setTelefono(dto.getTelefono());
        // Set other fields as needed (e.g., password, premium)
        usuario.setPassword(dto.getPassword());
        usuario.setPremium(dto.getIsPremium());
        return usuario;
    }
}
