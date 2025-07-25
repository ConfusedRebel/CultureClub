package com.cultureclub.cclub.controller;

import java.net.URI;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cultureclub.cclub.entity.Entrada;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.Authentication.AuthRequestDTO;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO loginRequest) {
    try {
        Usuario usuario = usuarioService.login(loginRequest.getEmail(), loginRequest.getPassword()).get();
        UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(usuario);
        return ResponseEntity.ok(usuarioDTO);
    } catch (IllegalArgumentException e) {
        // Credenciales inválidas
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    } catch (Exception e) {
        // Cualquier otro error no controlado
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
}


    @GetMapping("/{id}")
    public ResponseEntity<Object> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> result = usuarioService.getUsuarioById(id);
        if (result.isEmpty())
            return ResponseEntity.notFound().build();
        UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(result.get());
        return ResponseEntity.ok(usuarioDTO);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public String putUsuarioById(
            @PathVariable Long id,
            @ModelAttribute UsuarioDTO data,
            @RequestParam (value = "imagen", required = false) MultipartFile imagen
    ) {
        try {
            if (imagen != null && !imagen.isEmpty()) {
                byte[] bytes = imagen.getBytes();
                Blob imageBlob = new javax.sql.rowset.serial.SerialBlob(bytes);
                data.setFotoBlob(imageBlob);
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException("Error al leer la imagen", e);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Error al crear el Blob de la imagen", e);
        }
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

    @GetMapping("/{usuarioId}/seguidores")
    public ResponseEntity<List<UsuarioDTO>> getSeguidores(@PathVariable Long usuarioId) {
        List<UsuarioDTO> seguidores = ((List<Usuario>) usuarioService.getSeguidoresByUsuario(usuarioId))
                .stream().map(UsuarioMapper::toDTO).toList();
        return ResponseEntity.ok(seguidores);
    }

    @GetMapping("/{usuarioId}/seguidos")
    public ResponseEntity<List<UsuarioDTO>> getSeguidos(@PathVariable Long usuarioId) {
        List<UsuarioDTO> seguidos = ((List<Usuario>) usuarioService.getSeguidosByUsuario(usuarioId))
                .stream().map(UsuarioMapper::toDTO).toList();
        return ResponseEntity.ok(seguidos);
    }

    @DeleteMapping("/{UsuarioId}/seguir/{usuarioSeguidoId}")
    public String dejarDeSeguirUsuario(@PathVariable Long UsuarioId, @PathVariable Long usuarioSeguidoId) {
        usuarioService.seguirUsuario(UsuarioId, usuarioSeguidoId);
        return "Dejaste de seguir al usuario correctamente";
    }

    @GetMapping("{idUsuario}/entradas")
    public ResponseEntity<List<EntradaDTO>> getEntradasUsuario(@PathVariable Long idUsuario) {
        List<EntradaDTO> entradas = usuarioService.getEntradas(idUsuario)
                .stream().map(EntradaMapper::toDTO).toList();
        return ResponseEntity.ok(entradas);
    }
    

}
