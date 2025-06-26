package com.cultureclub.cclub.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultureclub.cclub.entity.Entrada;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.CalificacionDTO;
import com.cultureclub.cclub.entity.dto.EntradaDTO;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.dto.reporte.ReporteDTO;
import com.cultureclub.cclub.entity.reportes.Reporte;
import com.cultureclub.cclub.mapper.EntradaMapper;
import com.cultureclub.cclub.mapper.ReporteMapper;
import com.cultureclub.cclub.mapper.UsuarioMapper;
import com.cultureclub.cclub.service.Int.NotificacionService;
import com.cultureclub.cclub.service.Int.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> result = usuarioService.getUsuarioById(id);
        if (result.isEmpty())
            return ResponseEntity.notFound().build();
        UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(result.get());
        return ResponseEntity.ok(usuarioDTO);
    }

    @PutMapping("/{id}")
    public String putUsuarioById(@PathVariable Long id, @RequestBody UsuarioDTO data) {
        return usuarioService.updateUsuario(id, data);
    }

    @PostMapping("/{id}/reportar")
    public ResponseEntity<ReporteDTO> reportarUsuario(@PathVariable Long id, @RequestBody ReporteDTO reporte)
            throws Exception {
        reporte.setIdEmisor(id);
        Reporte result = usuarioService.reportarUsuario(reporte);
        return ResponseEntity.created(
                URI.create("/usuarios/" + id + "/reportes/" + result.getIdReporte()))
                .body(ReporteMapper.toDTO(result));
    }

    @PostMapping("{idUsuario}/calificar/{idEvento}")
    public ResponseEntity<Object> calificarEvento(@PathVariable Long idUsuario, @PathVariable Long idEvento,
            @RequestBody CalificacionDTO calificacionDTO) {
        Integer calificacion = calificacionDTO.getCalificacion();
        if (calificacion < 1 || calificacion > 5) {
            return ResponseEntity.badRequest().body("La calificación debe estar entre 1 y 5");
        }
        usuarioService.calificarEvento(idUsuario, idEvento, calificacion);
        return ResponseEntity.ok("Evento calificado correctamente");
    }

    @PostMapping("/{idUsuario}/comprar-entrada")
    public ResponseEntity<EntradaDTO> comprarEntrada(@PathVariable Long idUsuario,
            @RequestBody EntradaDTO entradaDTO) {
        Optional<Entrada> entrada = usuarioService.comprarEntrada(idUsuario, entradaDTO);
        EntradaDTO entradaResponse = EntradaMapper.toDTO(entrada.get());
        return ResponseEntity
                .created(URI.create("/usuarios/" + idUsuario + "/entradas/" + entradaResponse.getIdEntrada()))
                .body(entradaResponse);
    }

    @PutMapping("/{usuarioId}/seguir/{usuarioSeguidoId}")
    public String seguirUsuario(@PathVariable Long usuarioId, @PathVariable Long usuarioSeguidoId) {
        usuarioService.seguirUsuario(usuarioId, usuarioSeguidoId);
        return "Usuario seguido correctamente";
    }

    @PutMapping("/{usuarioId}/seguir-evento/{eventoId}")
    public String seguirEvento(@PathVariable Long usuarioId, @PathVariable Long eventoId) {
        usuarioService.seguirEvento(usuarioId, eventoId);
        return "Evento seguido correctamente";
    }

    @GetMapping("/{usuarioId}/eventos-asistidos")
    public ResponseEntity<Object> getEventosAsistidos(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioService.getEventosAsistidos(usuarioId));
    }

    @GetMapping("/{usuarioId}/notificaciones")
    public ResponseEntity<Object> getNotificaciones(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesUsuario(usuarioId));
    }

}
