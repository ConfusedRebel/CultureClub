package com.cultureclub.cclub.mapper;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.Notificacion;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.NotificacionDTO;

public class NotificacionMapper {
    public static NotificacionDTO toDTO(Notificacion notif) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setIdNotificacion(notif.getIdNotificacion());
        dto.setIdEvento(notif.getEvento().getIdEvento());
        dto.setIdUsuario(notif.getUsuario().getIdUsuario());
        dto.setMensaje(notif.getMensaje());
        dto.setFecha(notif.getFecha());
        return dto;
    }

    public static Notificacion toEntity(NotificacionDTO dto, Evento evento, Usuario usuario) {
        Notificacion n = new Notificacion();
        n.setIdNotificacion(dto.getIdNotificacion());
        n.setEvento(evento);
        n.setUsuario(usuario);
        n.setMensaje(dto.getMensaje());
        n.setFecha(dto.getFecha());
        return n;
    }
}
