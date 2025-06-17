package com.cultureclub.cclub.mapper;

import com.cultureclub.cclub.entity.Entrada;
import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.EntradaDTO;
import com.cultureclub.cclub.entity.enumeradores.TipoEntrada;

public class EntradaMapper {

    public static EntradaDTO toDTO(Entrada entrada) {
        EntradaDTO dto = new EntradaDTO();
        dto.setIdEntrada(entrada.getIdEntrada());
        dto.setTipoEntrada(entrada.getTipoEntrada().name());
        dto.setIdEvento(entrada.getEvento().getIdEvento());
        dto.setIdCompradorUsuario(entrada.getCompradorUsuario().getIdUsuario());
        dto.setFechaCompra(entrada.getFechaCompra());
        dto.setFechaUso(entrada.getFechaUso());
        dto.setPrecioPagado(entrada.getPrecioPagado());
        return dto;
    }

    public static Entrada toEntity(EntradaDTO dto, Evento evento, Usuario usuario) {
        Entrada entrada = new Entrada();
        entrada.setIdEntrada(dto.getIdEntrada());
        entrada.setTipoEntrada(TipoEntrada.valueOf(dto.getTipoEntrada()));
        entrada.setEvento(evento);
        entrada.setCompradorUsuario(usuario);
        entrada.setFechaCompra(dto.getFechaCompra());
        entrada.setFechaUso(dto.getFechaUso());
        entrada.setPrecioPagado(dto.getPrecioPagado());
        return entrada;
    }
}