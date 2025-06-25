package com.cultureclub.cclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cultureclub.cclub.entity.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUsuario_IdUsuario(Long idUsuario);
}
