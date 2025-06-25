package com.cultureclub.cclub.mapper;

import com.cultureclub.cclub.entity.Resena;
import com.cultureclub.cclub.entity.Evento;
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
        return dto;
    }

    public static Resena toEntity(ResenaDTO dto, Evento evento, Usuario usuario) {
        Resena r = new Resena();
        r.setIdResena(dto.getIdResena());
        r.setEvento(evento);
        r.setUsuario(usuario);
        r.setContenido(dto.getContenido());
        r.setFecha(dto.getFecha());
        return r;
    }
}
