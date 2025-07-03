package com.cultureclub.cclub.entity.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EventoAsistidoDTO {
    private String nombreEvento = "";
    private Date fechaEvento = new Date(System.currentTimeMillis());
}
