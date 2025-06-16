package com.cultureclub.cclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cultureclub.cclub.entity.reportes.ReporteUsuario;

@Repository
public interface ReporteUsuarioRepository extends JpaRepository<ReporteUsuario, Long> {
}
