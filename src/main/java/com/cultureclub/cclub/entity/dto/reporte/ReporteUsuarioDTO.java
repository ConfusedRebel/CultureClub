package com.cultureclub.cclub.entity.dto.reporte;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReporteUsuarioDTO extends ReporteDTO {
    private Long idUsuarioReportado;
}