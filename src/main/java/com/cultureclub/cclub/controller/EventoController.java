package com.cultureclub.cclub.controller;

import org.springframework.data.domain.Page;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.dto.EventoDTO;
import com.cultureclub.cclub.service.EventoService;
import com.mapper.EventoMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping("/{idUsuario}")
    public ResponseEntity<EventoDTO> publicarEvento(@RequestBody EventoDTO entity, @PathVariable Long idUsuario)
            throws Exception {
        EventoDTO evento = EventoMapper.toDTO(eventoService.createEvento(entity, idUsuario));
        return ResponseEntity.created(URI.create("/eventos/" + evento.getIdEvento())).body(evento);
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

}
