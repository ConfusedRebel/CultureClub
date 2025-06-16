package com.cultureclub.cclub.service;

import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.dto.reporte.ReporteDTO;
import com.cultureclub.cclub.entity.reportes.Reporte;

import java.util.Optional;

import com.cultureclub.cclub.entity.Usuario;

public interface UsuarioService {

    Optional<Usuario> getUsuarioById(Long id);

    String updateUsuario(Long id, UsuarioDTO data);

    Reporte reportarUsuario(ReporteDTO reporte) throws Exception;

}
