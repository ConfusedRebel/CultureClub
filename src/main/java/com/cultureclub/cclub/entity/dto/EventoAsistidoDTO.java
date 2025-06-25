package com.cultureclub.cclub.entity.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class EventoAsistidoDTO {
    private String nombreEvento;
    private Date fechaEvento;
}
