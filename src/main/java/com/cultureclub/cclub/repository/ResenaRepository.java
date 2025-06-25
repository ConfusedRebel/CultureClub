package com.cultureclub.cclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cultureclub.cclub.entity.Resena;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByEvento_IdEvento(Long idEvento);
    List<Resena> findByUsuario_IdUsuario(Long idUsuario);
}
