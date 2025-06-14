package com.mapper;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.dto.EventoDTO;

public class EventoMapper {

    public static EventoDTO toDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setIdEvento(evento.getIdEvento());
        dto.setIdOrganizador(evento.getUsuarioOrganizador().getIdUsuario());
        dto.setNombre(evento.getNombre());
        dto.setEntrada(evento.isEntrada());
        dto.setPrecio(evento.getPrecio());
        dto.setInicio(evento.getInicio());
        dto.setFin(evento.getFin());
        dto.setClase(evento.getClase().name());
        return dto;
    }

    public static Evento toEntity(EventoDTO dto, com.cultureclub.cclub.entity.Usuario organizador) {
        Evento evento = new Evento();
        evento.setIdEvento(dto.getIdEvento());
        evento.setUsuarioOrganizador(organizador);
        evento.setNombre(dto.getNombre());
        evento.setEntrada(dto.isEntrada());
        evento.setPrecio(dto.getPrecio());
        evento.setInicio(dto.getInicio());
        evento.setFin(dto.getFin());
        if (dto.getClase() != null) {
            evento.setClase(com.cultureclub.cclub.entity.ClaseEvento.valueOf(dto.getClase()));
        }
        return evento;
    }
}
