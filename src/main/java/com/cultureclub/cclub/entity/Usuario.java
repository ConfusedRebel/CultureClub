package com.cultureclub.cclub.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column
    private Ciudad ciudad;

    @Column
    private String email;

    @Column
    private String pass;

    @Column
    private int puntuacion = 0;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
    private List<Usuario> seguidos;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
    private List<Usuario> seguidores;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference
    private List<Entrada> entradas;
}
