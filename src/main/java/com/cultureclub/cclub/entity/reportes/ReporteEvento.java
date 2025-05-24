package com.cultureclub.cclub.entity.reportes;

import com.cultureclub.cclub.entity.Evento;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "reporte_evento")
public class ReporteEvento extends Reporte {

    @ManyToOne
    private Evento eventoReportado;
}