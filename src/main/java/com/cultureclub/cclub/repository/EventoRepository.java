package com.cultureclub.cclub.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.enumeradores.Ciudad;
import com.cultureclub.cclub.entity.enumeradores.ClaseEvento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long>, JpaSpecificationExecutor<Evento> {

    // No need to declare findAll here; JpaRepository already provides it.
    // Find by name (returns a list in case there are multiple matches)
    List<Evento> findByNombre(String nombre);

    Page<Evento> findByClase(ClaseEvento claseEvento, Pageable pageable);

    Page<Evento> findByUsuarioOrganizador_Ciudad(Ciudad ciudad, Pageable pageable);

    Page<Evento> findByPrecioLessThanEqual(int precio, Pageable pageable);

    List<Evento> findTop3ByOrderByCantidadVisitasDesc();

    List<Evento> findByNombreContainingIgnoreCase(String param);
}
