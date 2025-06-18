package com.cultureclub.cclub.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.EventoDTO;
import com.cultureclub.cclub.entity.enumeradores.ClaseEvento;
import com.cultureclub.cclub.repository.EventoRepository;
import com.cultureclub.cclub.repository.UsuarioRepository;
import com.cultureclub.cclub.service.Int.EventoService;
import com.cultureclub.cclub.service.Int.GestorUsuarioService;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private GestorUsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario getAuthenticatedUsuario() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new AccessDeniedException("Usuario no autenticado");
        }
        String email = auth.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("Usuario no encontrado"));
    }

    @Override
    public Page<Evento> getEventos(int page, int size) {
        return eventoRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Evento createEvento(EventoDTO entity, Long idUsuario) throws Exception {
        List<Evento> eventos = eventoRepository.findByNombre(entity.getNombre());
        if (!eventos.isEmpty()) {
            throw new IllegalArgumentException("Evento con el nombre '" + entity.getNombre() + "' ya existe.");
        } else {
            Evento evento = new Evento();
            evento.setNombre(entity.getNombre());
            evento.setEntrada(entity.isEntrada());
            evento.setPrecio(entity.getPrecio());
            evento.setInicio(entity.getInicio());
            evento.setFin(entity.getFin());
            evento.setClase(ClaseEvento.valueOf(entity.getClase()));
            // Buscar y asignar el organizador
            Usuario organizador = usuarioService.getUsuarioById(idUsuario)
                    .orElseThrow(() -> new IllegalArgumentException("Organizador no encontrado"));
            evento.setUsuarioOrganizador(organizador);
            // Initialize fields that can be null
            if (evento.getCalificacion() == null) {
                evento.setCalificacion(0);
            }
            if (evento.getCantidadVisitas() == null) {
                evento.setCantidadVisitas(0);
            }
            return eventoRepository.save(evento);
        }
    }

    @Override
    public Evento getEventoById(Long idEvento) {
        Optional<Evento> eventoOpt = eventoRepository.findById(idEvento);
        if (eventoOpt.isPresent()) {
            return eventoOpt.get();
        } else {
            throw new IllegalArgumentException("Evento no encontrado con ID: " + idEvento);
        }
    }

    @Override
    public Evento updateEvento(Long idEvento, EventoDTO entity, Long idUsuario) {
        Usuario authUser = getAuthenticatedUsuario();
        if (!authUser.getIdUsuario().equals(idUsuario)) {
            throw new AccessDeniedException("No autorizado para editar este evento");
        }

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado con ID: " + idEvento));

        if (!evento.getUsuarioOrganizador().getIdUsuario().equals(authUser.getIdUsuario())) {
            throw new AccessDeniedException("No autorizado para editar este evento");
        }

        if (entity.getNombre() != null) {
            evento.setNombre(entity.getNombre());
        }
        evento.setEntrada(entity.isEntrada());
        evento.setPrecio(entity.getPrecio());
        if (entity.getInicio() != null) {
            evento.setInicio(entity.getInicio());
        }
        if (entity.getFin() != null) {
            evento.setFin(entity.getFin());
        }
        if (entity.getClase() != null) {
            evento.setClase(ClaseEvento.valueOf(entity.getClase()));
        }

        return eventoRepository.save(evento);
    }

    @Override
    public Page<Evento> getEventosByClase(String clase, int page, int size) {
        if (page < 0 || size < 0) {
            throw new IllegalArgumentException("Los parámetros de paginación no pueden ser negativos");
        }
        ClaseEvento claseEvento;
        try {
            claseEvento = ClaseEvento.valueOf(clase.toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Clase de evento no válida: " + clase);
        }
        return eventoRepository.findByClase(claseEvento, PageRequest.of(page, size));
    }

}