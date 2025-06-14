package com.cultureclub.cclub.service;

import org.springframework.data.domain.Page;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.dto.EventoDTO;

public interface EventoService {

    public Page<Evento> getEventos(int page, int size);

    public Evento createEvento(EventoDTO entity, Long idUsuario) throws Exception;

}
