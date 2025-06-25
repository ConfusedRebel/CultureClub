package com.cultureclub.cclub.entity;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import com.cultureclub.cclub.entity.EventoAsistido;

import com.cultureclub.cclub.entity.enumeradores.Ciudad;
import com.cultureclub.cclub.entity.enumeradores.Rol;
import com.cultureclub.cclub.entity.reportes.ReporteError;
import com.cultureclub.cclub.entity.reportes.ReporteEvento;
import com.cultureclub.cclub.entity.reportes.ReporteUsuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Lob;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @ElementCollection(targetClass = Rol.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Set<Rol> roles = Set.of(Rol.USUARIO);
    
    @Column
    private String nombre;

    @Column
    private String apellidos;

    @Enumerated(EnumType.STRING)
    @Column
    private Ciudad ciudad;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private int puntuacion = 0;

    @Column
    private int telefono;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @ManyToMany // Many usuarios can follow many other usuarios
    @JoinTable(name = "usuario_seguidos", joinColumns = @JoinColumn(name = "seguidor_id"), inverseJoinColumns = @JoinColumn(name = "seguido_id"))
    private List<Usuario> seguidos;

    @ManyToMany(mappedBy = "seguidos") // Reverse relationship for followers
    private List<Usuario> seguidores;

    @ManyToMany(mappedBy = "seguidores")
    private List<Evento> eventosSeguidos = new ArrayList<>();

    @ElementCollection
    private List<EventoAsistido> eventosAsistidos = new ArrayList<>();

    @OneToMany(mappedBy = "compradorUsuario", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Entrada> entradas;

    @OneToMany(mappedBy = "usuarioOrganizador", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Evento> eventosOrganizados;

    @OneToMany(mappedBy = "usuarioEmisor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ReporteEvento> reportesEventos;

    @OneToMany(mappedBy = "usuarioEmisor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ReporteUsuario> reportesUsuarios;

    @OneToMany(mappedBy = "usuarioEmisor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ReporteError> reportesErrores;
}
