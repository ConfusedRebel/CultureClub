package com.cultureclub.cclub.controller;

import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.dto.reporte.ReporteDTO;
import com.cultureclub.cclub.entity.reportes.Reporte;
import com.cultureclub.cclub.service.UsuarioService;
import com.mapper.ReporteMapper;
import com.mapper.UsuarioMapper;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> postMethodName(@PathVariable Long id) {
        Optional<Usuario> result = usuarioService.getUsuarioById(id);
        if (result.isEmpty())
            return ResponseEntity.notFound().build();
        UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(result.get());
        return ResponseEntity.ok(usuarioDTO);
    }

    @PutMapping("/{id}")
    public String putMethodName(@PathVariable Long id, @RequestBody UsuarioDTO data) {
        return usuarioService.updateUsuario(id, data);
    }

    @PostMapping("/{id}/reportar")
    public ResponseEntity<ReporteDTO> reportarUsuario(@PathVariable Long id, @RequestBody ReporteDTO reporte)
            throws Exception {
        reporte.setIdEmisor(id);
        Optional<Reporte> result = usuarioService.reportarUsuario(reporte);
        if (result.isEmpty()) {
            throw new Exception("Error al reportar usuario");
        } else {
            return ResponseEntity.created(
                    URI.create("/usuarios/" + id + "/reportes/" + result.get().getIdReporte()))
                    .body(ReporteMapper.toDTO(result.get()));
        }
    }
}
