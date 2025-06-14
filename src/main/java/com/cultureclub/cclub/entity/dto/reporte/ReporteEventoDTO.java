package com.cultureclub.cclub.entity.dto.reporte;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReporteEventoDTO extends ReporteDTO {
    private Long idEventoReportado;
}