package com.cultureclub.cclub.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class EventoAsistido {
    @Column(name = "nombre_evento")
    private String nombreEvento;

    @Column(name = "fecha_evento")
    private Date fechaEvento;
}
