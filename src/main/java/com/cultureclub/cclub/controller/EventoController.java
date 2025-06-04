package com.cultureclub.cclub.controller;

import org.springframework.data.domain.Page;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.dto.EventoDTO;
import com.cultureclub.cclub.service.EventoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping("")
    public ResponseEntity<Object> postMethodName(@RequestBody EventoDTO entity) throws Exception {
        Evento evento = eventoService.createEvento(entity);
        return ResponseEntity.created(URI.create("/eventos/" + evento.getIdEvento())).body(evento);
    }

    @GetMapping("")
    public ResponseEntity<Page<Evento>> getEventos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page >= 0 && size >= 0) {
            return ResponseEntity.ok(eventoService.getEventos(page, size));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
