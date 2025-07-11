package com.cultureclub.cclub.entity;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cultureclub.cclub.entity.enumeradores.Ciudad;
import com.cultureclub.cclub.entity.enumeradores.ClaseEvento;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    @ManyToOne
    @JoinColumn(name = "organizador_id", nullable = false)
    private Usuario usuarioOrganizador;

    @Column
    private String nombre;

    @Column
    private String descripcion;

    @Column
    private boolean entrada;

    @Column
    private int precio;

    @Column
    private Date inicio;

    @Column
    private Date fin;

    @Column
    private Double latitud;

    @Column
    private Double longitud;

    @Column
    private Integer calificacion = 0;

    @Column
    private Integer cantidadVisitas = 0;

    @ManyToMany
    @JoinTable(name = "evento_seguidores", joinColumns = @JoinColumn(name = "evento_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<Usuario> seguidores = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaseEvento clase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ciudad ciudad;

    @Column
    private int clasificacion;

    @Lob
    @Column(name = "imagen")
    private Blob imagen;

    @OneToMany(mappedBy = "evento")
    private List<Entrada> entradas = new ArrayList<>();
    
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resena> resenas;

}
