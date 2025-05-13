package com.cultureclub.cclub.entity;

import java.sql.Date;

import org.hibernate.annotations.Collate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    @Column
    private TipoEntrada tipoEntrada;

    @Column
    private Evento evento;;

    @Column
    private Usuario usuario;

    @Column
    private Date fechaCompra;

    @Column
    private Date fechaUso;

    @Column
    private int precioPagdo;
}
