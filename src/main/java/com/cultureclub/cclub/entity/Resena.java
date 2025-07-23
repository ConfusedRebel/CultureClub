package com.cultureclub.cclub.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "resena")
@Data
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResena;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column
    private String contenido;

    @Column
    private int calificacion = 0;

    @Column
    private Date fecha;

    public String getNombreUsuario() {
        return usuario != null ? usuario.getNombre() + " " + usuario.getApellidos() : null;
    }

    public String getNombreEvento() {
        return evento != null ? evento.getNombre() : null;
    }
}
