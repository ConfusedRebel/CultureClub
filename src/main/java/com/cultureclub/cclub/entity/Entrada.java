package com.cultureclub.cclub.entity;

import java.sql.Date;

import com.cultureclub.cclub.entity.enumeradores.TipoEntrada;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "entrada")
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntrada;

    @Enumerated(EnumType.STRING)
    @Column
    private TipoEntrada tipoEntrada;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario compradorUsuario;

    @Column(nullable = false)
    private Date fechaCompra;

    @Column(nullable = false)
    private Date fechaUso;

    @Column(nullable = false)
    private int precioPagado;
}
