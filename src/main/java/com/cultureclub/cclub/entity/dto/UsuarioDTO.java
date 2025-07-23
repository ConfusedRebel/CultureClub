package com.cultureclub.cclub.entity.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.cultureclub.cclub.entity.enumeradores.Rol;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long idUsuario = 0L;
    private Set<Rol> roles = new HashSet<>();
    private String nombre = "";
    private String apellidos = "";
    private String ciudad = "";
    private String email = "";
    private String password = "";
    private Integer telefono = 0;
    private Integer puntuacion = 0;
    private byte[] foto = new byte[0];
    private MultipartFile imagen = null;
}
