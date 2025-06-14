package com.cultureclub.cclub.entity.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long idUsuario;
    private Boolean isPremium;
    private String nombre;
    private String apellidos;
    private String ciudad;
    private String email;
    private String password;
    private int telefono;
    private int puntuacion;
}
