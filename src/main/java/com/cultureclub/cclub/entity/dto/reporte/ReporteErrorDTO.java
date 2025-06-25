package com.cultureclub.cclub.entity.dto.reporte;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReporteErrorDTO extends ReporteDTO {
    private String urlAfectada = "";
    private String severidad = "";
}