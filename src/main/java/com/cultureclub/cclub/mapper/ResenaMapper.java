package com.cultureclub.cclub.mapper;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.Resena;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.ResenaDTO;

public class ResenaMapper {
    public static ResenaDTO toDTO(Resena resena) {
        ResenaDTO dto = new ResenaDTO();
        dto.setIdResena(resena.getIdResena());
        dto.setIdEvento(resena.getEvento().getIdEvento());
        dto.setIdUsuario(resena.getUsuario().getIdUsuario());
        dto.setContenido(resena.getContenido());
        dto.setFecha(resena.getFecha());
        dto.setCalificacion(resena.getCalificacion());
        dto.setNombreEvento(resena.getNombreEvento());
        dto.setNombreUsuario(resena.getNombreUsuario());
        return dto;
    }

    public static Resena toEntity(ResenaDTO dto, Evento evento, Usuario usuario) {
        Resena r = new Resena();
        r.setIdResena(dto.getIdResena());
        r.setEvento(evento);
        r.setUsuario(usuario);
        r.setContenido(dto.getContenido());
        r.setFecha(dto.getFecha());
        r.setCalificacion(dto.getCalificacion());
        return r;
    }

    public static void updateEntityIfPresent(Resena resena, ResenaDTO dto) {
    if (dto.getContenido() != null) {
        resena.setContenido(dto.getContenido());
    }
    if (dto.getFecha() != null) {
        resena.setFecha(dto.getFecha());
    }
    // Si quieres permitir actualizar usuario o evento, agrega validaciones similares:
    // if (dto.getIdUsuario() != null) { ... }
    // if (dto.getIdEvento() != null) { ... }
}
}
