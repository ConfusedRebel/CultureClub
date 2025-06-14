package com.mapper;

import com.cultureclub.cclub.entity.dto.reporte.ReporteDTO;
import com.cultureclub.cclub.entity.dto.reporte.ReporteErrorDTO;
import com.cultureclub.cclub.entity.dto.reporte.ReporteEventoDTO;
import com.cultureclub.cclub.entity.dto.reporte.ReporteUsuarioDTO;
import com.cultureclub.cclub.entity.reportes.Reporte;
import com.cultureclub.cclub.entity.reportes.ReporteError;
import com.cultureclub.cclub.entity.reportes.ReporteEvento;
import com.cultureclub.cclub.entity.reportes.ReporteUsuario;

public class ReporteMapper {
    public static ReporteDTO toDTO(Reporte reporte) {
        if (reporte instanceof ReporteError error) {
            ReporteErrorDTO dto = new ReporteErrorDTO();
            dto.setUrlAfectada(error.getUrlAfectada());
            dto.setSeveridad(error.getSeveridad());
            setBaseFields(dto, reporte);
            return dto;
        } else if (reporte instanceof ReporteUsuario usuario) {
            ReporteUsuarioDTO dto = new ReporteUsuarioDTO();
            dto.setIdUsuarioReportado(usuario.getUsuarioReportado().getIdUsuario());
            setBaseFields(dto, reporte);
            return dto;
        } else if (reporte instanceof ReporteEvento evento) {
            ReporteEventoDTO dto = new ReporteEventoDTO();
            dto.setIdEventoReportado(evento.getEventoReportado().getIdEvento());
            setBaseFields(dto, reporte);
            return dto;
        } else {
            // fallback to base DTO
            ReporteDTO dto = new ReporteDTO();
            setBaseFields(dto, reporte);
            return dto;
        }
    }

    private static void setBaseFields(ReporteDTO dto, Reporte reporte) {
        dto.setIdReporte(reporte.getIdReporte());
        dto.setIdEmisor(reporte.getUsuarioEmisor().getIdUsuario());
        dto.setFecha(reporte.getFecha());
        dto.setMotivo(reporte.getMotivo());
        dto.setDescripcion(reporte.getDescripcion());
    }

    public static Reporte toEntity(ReporteDTO dto) {
        if (dto instanceof ReporteErrorDTO errorDto) {
            ReporteError reporte = new ReporteError();
            reporte.setUrlAfectada(errorDto.getUrlAfectada());
            reporte.setSeveridad(errorDto.getSeveridad());
            setBaseFieldsInEntity(dto, reporte);
            return reporte;
        } else if (dto instanceof ReporteUsuarioDTO usuarioDto) {
            ReporteUsuario reporte = new ReporteUsuario();
            // Create a UsuarioDTO with the id before mapping to entity
            com.cultureclub.cclub.entity.dto.UsuarioDTO usuarioDtoTemp = new com.cultureclub.cclub.entity.dto.UsuarioDTO();
            usuarioDtoTemp.setIdUsuario(usuarioDto.getIdUsuarioReportado());
            reporte.setUsuarioReportado(UsuarioMapper.toEntity(usuarioDtoTemp));
            setBaseFieldsInEntity(dto, reporte);
            return reporte;
        } else if (dto instanceof ReporteEventoDTO eventoDto) {
            ReporteEvento reporte = new ReporteEvento();
            // Assuming EventoMapper is available to convert event ID to Evento entity
            com.cultureclub.cclub.entity.dto.EventoDTO eventoDtoTemp = new com.cultureclub.cclub.entity.dto.EventoDTO();
            eventoDtoTemp.setIdEvento(eventoDto.getIdEventoReportado());
            // Provide a Usuario object as the second argument (replace 'null' with the
            // appropriate Usuario if available)
            reporte.setEventoReportado(EventoMapper.toEntity(eventoDtoTemp, null));
            setBaseFieldsInEntity(dto, reporte);
            return reporte;
        }
        throw new IllegalArgumentException("Unsupported ReporteDTO type: " + dto.getClass().getName());
    }

    private static void setBaseFieldsInEntity(ReporteDTO dto, Reporte reporte) {
        reporte.setIdReporte(dto.getIdReporte());
        reporte.setFecha(dto.getFecha());
        reporte.setMotivo(dto.getMotivo());
        reporte.setDescripcion(dto.getDescripcion());
    }

}
