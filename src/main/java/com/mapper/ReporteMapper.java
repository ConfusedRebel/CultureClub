package com.mapper;

import com.cultureclub.cclub.entity.Usuario;
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

    public static Reporte toEntity(ReporteDTO dto, Usuario emisor) {
        Reporte reporte;
        if (dto instanceof ReporteErrorDTO errorDto) {
            ReporteError r = new ReporteError();
            r.setUrlAfectada(errorDto.getUrlAfectada());
            r.setSeveridad(errorDto.getSeveridad());
            reporte = r;
        } else if (dto instanceof ReporteUsuarioDTO usuarioDto) {
            ReporteUsuario r = new ReporteUsuario();
            com.cultureclub.cclub.entity.dto.UsuarioDTO usuarioDtoTemp = new com.cultureclub.cclub.entity.dto.UsuarioDTO();
            usuarioDtoTemp.setIdUsuario(usuarioDto.getIdUsuarioReportado());
            r.setUsuarioReportado(UsuarioMapper.toEntity(usuarioDtoTemp));
            reporte = r;
        } else if (dto instanceof ReporteEventoDTO eventoDto) {
            ReporteEvento r = new ReporteEvento();
            com.cultureclub.cclub.entity.dto.EventoDTO eventoDtoTemp = new com.cultureclub.cclub.entity.dto.EventoDTO();
            eventoDtoTemp.setIdEvento(eventoDto.getIdEventoReportado());
            r.setEventoReportado(EventoMapper.toEntity(eventoDtoTemp, null));
            reporte = r;
        } else {
            throw new IllegalArgumentException("Unsupported ReporteDTO type: " + dto.getClass().getName());
        }
        setBaseFieldsInEntity(dto, reporte);
        reporte.setUsuarioEmisor(emisor); // <-- Set the emisor here!
        return reporte;
    }

    private static void setBaseFieldsInEntity(ReporteDTO dto, Reporte reporte) {
        reporte.setIdReporte(dto.getIdReporte());
        reporte.setFecha(dto.getFecha());
        reporte.setMotivo(dto.getMotivo());
        reporte.setDescripcion(dto.getDescripcion());
    }

}
