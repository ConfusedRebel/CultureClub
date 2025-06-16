package com.cultureclub.cclub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cultureclub.cclub.entity.reportes.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
}