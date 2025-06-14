package com.cultureclub.cclub.entity.dto.reporte;

import java.sql.Date;

import lombok.Data;

@Data
public class ReporteDTO {
    private Long idReporte;
    private Long idEmisor;
    private Date fecha;
    private String motivo;
    private String descripcion;
    private String tipo;
}
