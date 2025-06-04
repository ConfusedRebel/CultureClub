package com.cultureclub.cclub.entity;

import java.util.List;

import jakarta.persistence.Column;
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

    @Column
    private Boolean premium;

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

    @ManyToMany // Many usuarios can follow many other usuarios
    @JoinTable(name = "usuario_seguidos", joinColumns = @JoinColumn(name = "seguidor_id"), inverseJoinColumns = @JoinColumn(name = "seguido_id"))
    private List<Usuario> seguidos;

    @ManyToMany(mappedBy = "seguidos") // Reverse relationship for followers
    private List<Usuario> seguidores;

    @OneToMany(mappedBy = "compradorUsuario")
    private List<Entrada> entradas;

    @OneToMany(mappedBy = "usuarioOrganizador")
    private List<Evento> eventosOrganizados;
}
