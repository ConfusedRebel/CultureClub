package com.cultureclub.cclub.service.Int;

import java.util.List;

import com.cultureclub.cclub.entity.Resena;
import com.cultureclub.cclub.entity.dto.ResenaDTO;

public interface ResenaService {
    Resena agregarResena(Long idUsuario, Long idEvento, ResenaDTO resena);
    List<Resena> obtenerResenasEvento(Long idEvento);
    List<Resena> obtenerResenasUsuario(Long idUsuario);
}
