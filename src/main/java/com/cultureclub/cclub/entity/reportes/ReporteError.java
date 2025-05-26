package com.cultureclub.cclub.entity.reportes;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "reporte_error")
public class ReporteError extends Reporte {

    private String urlAfectada;
    private String severidad;
}