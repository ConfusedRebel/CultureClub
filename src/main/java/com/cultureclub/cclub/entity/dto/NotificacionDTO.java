package com.cultureclub.cclub.entity.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class NotificacionDTO {
    private Long idNotificacion;
    private Long idEvento;
    private Long idUsuario;
    private String mensaje;
    private Date fecha;
}
