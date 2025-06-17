package com.cultureclub.cclub.service.Int;

import com.cultureclub.cclub.entity.dto.EntradaDTO;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.dto.reporte.ReporteDTO;
import com.cultureclub.cclub.entity.reportes.Reporte;

import java.util.Optional;

import com.cultureclub.cclub.entity.Entrada;
import com.cultureclub.cclub.entity.Usuario;

public interface UsuarioService {

    Optional<Usuario> getUsuarioById(Long id);

    String updateUsuario(Long id, UsuarioDTO data);

    Reporte reportarUsuario(ReporteDTO reporte) throws Exception;

    void calificarEvento(Long idUsuario, Long idEvento, int calificacion);

    Optional<Entrada> comprarEntrada(Long idUsuario, EntradaDTO entradaDTO);

    void seguirUsuario(Long usuarioId, Long usuarioSeguidoId);

}
