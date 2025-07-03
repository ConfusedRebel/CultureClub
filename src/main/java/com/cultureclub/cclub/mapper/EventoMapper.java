package com.cultureclub.cclub.mapper;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.EventoDTO;
import com.cultureclub.cclub.entity.enumeradores.ClaseEvento;

public class EventoMapper {

    private static final String UPLOAD_DIR = "uploads/";

    public static EventoDTO toDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setIdEvento(evento.getIdEvento());
        dto.setIdOrganizador(evento.getUsuarioOrganizador().getIdUsuario());
        dto.setNombre(evento.getNombre());
        dto.setDescripcion(evento.getDescripcion());
        dto.setEntrada(evento.isEntrada());
        dto.setPrecio(evento.getPrecio());
        dto.setInicio(evento.getInicio());
        dto.setFin(evento.getFin());
        dto.setLatitud(evento.getLatitud());
        dto.setLongitud(evento.getLongitud());
        dto.setClase(evento.getClase().name());
        dto.setCiudad(evento.getCiudad().name());
        dto.setCalificacion(evento.getCalificacion());
        if (evento.getImagen() != null) {
            try {
                int blobLength = (int) evento.getImagen().length();
                byte[] bytes = evento.getImagen().getBytes(1, blobLength);
                String base64 = java.util.Base64.getEncoder().encodeToString(bytes);
                dto.setImagen64(base64); // El campo imagen del DTO debe ser String
        } catch (Exception e) {
            dto.setImagen(null);
        }
    } else {
        dto.setImagen(null);
    }
        return dto;
    }

    public static Evento toEntity(EventoDTO dto, Usuario organizador) {
        Evento evento = new Evento();
        evento.setIdEvento(dto.getIdEvento());
        evento.setUsuarioOrganizador(organizador);
        evento.setNombre(dto.getNombre());
        evento.setDescripcion(dto.getDescripcion());
        evento.setEntrada(dto.isEntrada());
        evento.setPrecio(dto.getPrecio());
        evento.setInicio(dto.getInicio());
        evento.setFin(dto.getFin());
        evento.setLatitud(dto.getLatitud());
        evento.setLongitud(dto.getLongitud());
        evento.setCalificacion(dto.getCalificacion());
        evento.setImagen(dto.getImagen()); // Esto puede ser el nombre del archivo
        // Clase
        if (dto.getClase() != null) {
            evento.setClase(ClaseEvento.valueOf(dto.getClase()));
        }

        // Ciudad
        if (dto.getCiudad() != "") {
            evento.setCiudad(com.cultureclub.cclub.entity.enumeradores.Ciudad.valueOf(dto.getCiudad()));
        } else{
            evento.setCiudad(com.cultureclub.cclub.entity.enumeradores.Ciudad.CABA); // Valor por defecto
        }
        return evento;
    }
}
