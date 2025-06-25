package com.cultureclub.cclub.mapper;

import com.cultureclub.cclub.entity.EventoAsistido;
import com.cultureclub.cclub.entity.dto.EventoAsistidoDTO;

public class EventoAsistidoMapper {
    public static EventoAsistidoDTO toDTO(EventoAsistido e) {
        EventoAsistidoDTO dto = new EventoAsistidoDTO();
        dto.setNombreEvento(e.getNombreEvento());
        dto.setFechaEvento(e.getFechaEvento());
        return dto;
    }

    public static EventoAsistido toEntity(EventoAsistidoDTO dto) {
        EventoAsistido e = new EventoAsistido();
        e.setNombreEvento(dto.getNombreEvento());
        e.setFechaEvento(dto.getFechaEvento());
        return e;
    }
}
