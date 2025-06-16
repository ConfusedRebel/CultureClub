package com.cultureclub.cclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cultureclub.cclub.entity.Entrada;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    // Additional methods can be added as needed, for example:
    // List<Entrada> findByEventoId(Long eventoId);
    // List<Entrada> findByUsuarioId(Long usuarioId);
    // Optional<Entrada> findByIdAndUsuarioId(Long id, Long usuarioId);

}
