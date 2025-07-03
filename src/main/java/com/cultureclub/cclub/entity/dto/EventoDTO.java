package com.cultureclub.cclub.entity.dto;

import java.sql.Blob;
import java.util.Date;

import lombok.Data;

@Data
public class EventoDTO {
    private long idEvento = 0L;
    private long idOrganizador = 0L;
    private String nombre = "";
    private String descripcion = "";
    private boolean entrada = false;
    private int precio = 0;
    private Date inicio;
    private Date fin;
    private Double latitud = 0.0;
    private Double longitud = 0.0;
    private String ciudad; // Ciudad del evento, por defecto vacío
    private String clase = "";
    private Blob imagen;
    private int calificacion = 0; // Calificación del evento, por defecto 0
    private String imagen64;
}

