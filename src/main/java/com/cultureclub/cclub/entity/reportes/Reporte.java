package com.cultureclub.cclub.entity.reportes;

import java.sql.Date;

import com.cultureclub.cclub.entity.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass // Indicates this is a base class for other entities
public abstract class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReporte;

    @ManyToOne
    @JoinColumn(name = "emisor_id", nullable = false)
    private Usuario usuarioEmisor;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private String motivo;

    @Column
    private String descripcion;
}