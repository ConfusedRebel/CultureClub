package com.cultureclub.cclub.entity.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ResenaDTO {
    private Long idResena = 0L;
    private Long idEvento = 0L;
    private Long idUsuario = 0L;
    private String contenido = "";
    private Date fecha = new Date(System.currentTimeMillis());
}
