package com.cultureclub.cclub.entity.reportes;

import com.cultureclub.cclub.entity.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "reporte_usuario")
public class ReporteUsuario extends Reporte {

    @ManyToOne
    private Usuario usuarioReportado;
}