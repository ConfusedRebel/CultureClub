package com.cultureclub.cclub.service.Int;

import java.util.List;

import com.cultureclub.cclub.entity.Notificacion;

public interface NotificacionService {
    void enviarNotificacion(Long idEvento, String mensaje);
    List<Notificacion> obtenerNotificacionesUsuario(Long idUsuario);
}
