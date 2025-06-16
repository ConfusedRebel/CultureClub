package com.cultureclub.cclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cultureclub.cclub.entity.reportes.ReporteEvento;

@Repository
public interface ReporteEventoRepository extends JpaRepository<ReporteEvento, Long> {
}
