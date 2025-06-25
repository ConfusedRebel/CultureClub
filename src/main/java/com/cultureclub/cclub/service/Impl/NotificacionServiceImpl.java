package com.cultureclub.cclub.service.Impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.Notificacion;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.repository.EventoRepository;
import com.cultureclub.cclub.repository.NotificacionRepository;
import com.cultureclub.cclub.service.Int.NotificacionService;

@Service
public class NotificacionServiceImpl implements NotificacionService {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private NotificacionRepository notificacionRepository;

    @Override
    public void enviarNotificacion(Long idEvento, String mensaje) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado"));
        Date fecha = new Date(System.currentTimeMillis());
        for (Usuario seguidor : evento.getSeguidores()) {
            Notificacion n = new Notificacion();
            n.setEvento(evento);
            n.setUsuario(seguidor);
            n.setMensaje(mensaje);
            n.setFecha(fecha);
            notificacionRepository.save(n);
        }
    }

    @Override
    public List<Notificacion> obtenerNotificacionesUsuario(Long idUsuario) {
        return notificacionRepository.findByUsuario_IdUsuario(idUsuario);
    }
}
