package com.cultureclub.cclub.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.dto.reporte.ReporteDTO;
import com.cultureclub.cclub.entity.reportes.Reporte;

import com.cultureclub.cclub.repository.UsuarioRepository;
import com.mapper.ReporteMapper;
import com.cultureclub.cclub.repository.ReporteRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ReporteRepository reporteRepository;

    @Override
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public String updateUsuario(Long id, UsuarioDTO data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUsuario'");
    }

    @Override
    public Optional<Reporte> reportarUsuario(ReporteDTO reporte) {
        return reporteRepository.createReporte(ReporteMapper.toEntity(reporte));
    }

}
