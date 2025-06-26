package com.cultureclub.cclub.controller;

import org.springframework.data.domain.Page;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.dto.EventoDTO;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.mapper.EventoMapper;
import com.cultureclub.cclub.service.Int.EventoService;
import com.cultureclub.cclub.service.Int.NotificacionService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping("/{idUsuario}")
    public ResponseEntity<EventoDTO> publicarEvento(@RequestBody EventoDTO entity, @PathVariable Long idUsuario)
            throws Exception {
        EventoDTO evento = EventoMapper.toDTO(eventoService.createEvento(entity, idUsuario));
        return ResponseEntity.created(URI.create("/eventos/" + evento.getIdEvento())).body(evento);
    }

    @PutMapping("/{idUsuario}/{idEvento}")
    public ResponseEntity<EventoDTO> actualizarEvento(
            @RequestBody EventoDTO entity,
            @PathVariable Long idUsuario,
            @PathVariable Long idEvento) {
        Evento evento = eventoService.updateEvento(idEvento, entity, idUsuario);
        return ResponseEntity.ok(EventoMapper.toDTO(evento));
    }

    @PostMapping("/{idEvento}/notificar")
    public ResponseEntity<String> notificarSeguidores(
            @PathVariable Long idEvento,
            @RequestBody String mensaje) {
        notificacionService.enviarNotificacion(idEvento, mensaje);
        return ResponseEntity.ok("Notificacion enviada");
    }

    @GetMapping("")
    public ResponseEntity<Page<EventoDTO>> getEventos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page >= 0 && size >= 0) {
            Page<Evento> eventoPage = eventoService.getEventos(page, size);
            Page<EventoDTO> dtoPage = eventoPage.map(EventoMapper::toDTO);
            return ResponseEntity.ok(dtoPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{idEvento}")
    public ResponseEntity<EventoDTO> getEventoById(@PathVariable Long idEvento) {
        Evento evento = eventoService.getEventoById(idEvento);
        return ResponseEntity.ok(EventoMapper.toDTO(evento));
    }

    @GetMapping("/clase/{clase}")
    public ResponseEntity<Page<EventoDTO>> getEventosByClase(
            @PathVariable String clase,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page >= 0 && size >= 0) {
            Page<Evento> eventoPage = eventoService.getEventosByClase(clase, page, size);
            Page<EventoDTO> dtoPage = eventoPage.map(EventoMapper::toDTO);
            return ResponseEntity.ok(dtoPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<Page<EventoDTO>> getEventosByCiudad(
            @PathVariable String ciudad,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page >= 0 && size >= 0) {
            Page<Evento> eventoPage = eventoService.getEventosByCiudad(ciudad, page, size);
            Page<EventoDTO> dtoPage = eventoPage.map(EventoMapper::toDTO);
            return ResponseEntity.ok(dtoPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/precio/{precio}")
    public ResponseEntity<Page<EventoDTO>> getEventosByPrecio(
            @PathVariable int precio,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page >= 0 && size >= 0) {
            Page<Evento> eventoPage = eventoService.getEventosByPrecio(precio, page, size);
            Page<EventoDTO> dtoPage = eventoPage.map(EventoMapper::toDTO);
            return ResponseEntity.ok(dtoPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping ("/{idEvento}")
    public ResponseEntity<Boolean> eliminarEvento(@PathVariable Long idEvento) {
        Boolean isElim = eventoService.deleteEvento(idEvento);
        return ResponseEntity.ok(isElim);
    }
    @GetMapping("/ídx/{idEvento}/seguidores")
    public ResponseEntity<List<UsuarioDTO>> getSeguidoresByEvento(@PathVariable Long idEvento) {
        List<UsuarioDTO> seguidores = eventoService.getSeguidoresByEvento(idEvento);
        return ResponseEntity.ok(seguidores);
    }
}
}
