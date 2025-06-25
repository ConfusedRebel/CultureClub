package com.cultureclub.cclub.entity.dto;

import java.util.Set;

import com.cultureclub.cclub.entity.enumeradores.Rol;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long idUsuario;
    private Set<Rol> roles;
    private String nombre;
    private String apellidos;
    private String ciudad;
    private String email;
    private String password;
    private Integer telefono = 0;
    private Integer puntuacion = 0;
    private byte[] foto;
}
