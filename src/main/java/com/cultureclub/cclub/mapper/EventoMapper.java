package com.cultureclub.cclub.mapper;

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
        dto.setLatitud(evento.getLatitud());
        dto.setLongitud(evento.getLongitud());
        dto.setClase(evento.getClase().name());
        dto.setImagen(evento.getImagen());
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
        evento.setLatitud(dto.getLatitud());
        evento.setLongitud(dto.getLongitud());
        if (dto.getClase() != null) {
            evento.setClase(com.cultureclub.cclub.entity.enumeradores.ClaseEvento.valueOf(dto.getClase()));
        }
        evento.setImagen(dto.getImagen());
        return evento;
    }
}
