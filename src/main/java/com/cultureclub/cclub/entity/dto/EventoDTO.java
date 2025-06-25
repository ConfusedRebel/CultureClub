package com.cultureclub.cclub.entity.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class EventoDTO {
    private long idEvento = 0L;
    private long idOrganizador = 0L;
    private String nombre = "";
    private String descripcion = "";
    private boolean entrada = false;
    private int precio = 0;
    private Date inicio = new Date(System.currentTimeMillis());
    private Date fin = new Date(System.currentTimeMillis());
    private Double latitud = 0.0;
    private Double longitud = 0.0;
    private String clase = "";
    private byte[] imagen = new byte[0];
}
