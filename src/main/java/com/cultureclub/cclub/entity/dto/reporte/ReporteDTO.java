package com.cultureclub.cclub.entity.dto.reporte;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.sql.Date;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ReporteEventoDTO.class, name = "evento"),
        @JsonSubTypes.Type(value = ReporteUsuarioDTO.class, name = "usuario"),
        @JsonSubTypes.Type(value = ReporteErrorDTO.class, name = "error")
})
public class ReporteDTO {
    private Long idReporte;
    private Long idEmisor;
    private Date fecha;
    private String motivo;
    private String descripcion;
}