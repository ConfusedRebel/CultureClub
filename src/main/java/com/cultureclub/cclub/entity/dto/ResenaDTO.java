package com.cultureclub.cclub.entity.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ResenaDTO {
    private Long idResena;
    private Long idEvento;
    private Long idUsuario;
    private String contenido;
    private Date fecha;
}
