package com.cultureclub.cclub.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cultureclub.cclub.entity.Ciudad;
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
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        } else {
            Usuario usuario = usuarioOpt.get();
            usuario.setNombre(data.getNombre() != null ? data.getNombre() : usuario.getNombre());
            usuario.setEmail(data.getEmail() != null ? data.getEmail() : usuario.getEmail());
            usuario.setApellidos(data.getApellidos() != null ? data.getApellidos() : usuario.getApellidos());
            usuario.setCiudad(data.getCiudad() != null ? Ciudad.valueOf(data.getCiudad()) : usuario.getCiudad());
            usuario.setTelefono(data.getTelefono() != null ? data.getTelefono() : usuario.getTelefono());
            usuario.setPremium(data.getIsPremium() != null ? data.getIsPremium() : usuario.getPremium());
            usuario.setPassword(data.getPassword() != null ? data.getPassword() : usuario.getPassword());

            usuarioRepository.save(usuario);
            return "Usuario actualizado correctamente";
        }
    }

    @Override
    public Reporte reportarUsuario(ReporteDTO reporte) {
        Optional<Usuario> usuarioEmisor = usuarioRepository.findById(reporte.getIdEmisor());
        if (usuarioEmisor.isEmpty()) {
            throw new IllegalArgumentException("Usuario emisor no encontrado con ID: " + reporte.getIdEmisor());
        }
        return reporteRepository.save(ReporteMapper.toEntity(reporte, usuarioEmisor.get()));
    }

}
