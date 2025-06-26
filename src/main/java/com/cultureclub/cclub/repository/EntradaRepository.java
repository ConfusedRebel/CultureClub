package com.cultureclub.cclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cultureclub.cclub.entity.Entrada;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    public List<Entrada> findByCompradorUsuario_IdUsuario(Long idUsuario);

    // Additional methods can be added as needed, for example:
    // List<Entrada> findByEventoId(Long eventoId);
    // List<Entrada> findByUsuarioId(Long usuarioId);
    // Optional<Entrada> findByIdAndUsuarioId(Long id, Long usuarioId);

}
