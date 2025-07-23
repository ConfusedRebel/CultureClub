package com.cultureclub.cclub.controller;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.dto.EventoDTO;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.mapper.EventoMapper;
import com.cultureclub.cclub.mapper.UsuarioMapper;
import com.cultureclub.cclub.service.Int.EventoService;
import com.cultureclub.cclub.service.Int.NotificacionService;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventoDTO> publicarEvento(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date inicio,
            @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin,
            @RequestParam("clase") String clase,
            @RequestParam("entrada") boolean entrada,
            @RequestParam("ciudad") String ciudad,
            @RequestParam("idOrganizador") Long idOrganizador,
            @RequestParam("imagen") MultipartFile imagen
    ) throws IOException, Exception {
        EventoDTO dto = new EventoDTO();
        dto.setNombre(nombre);
        dto.setDescripcion(descripcion);
        dto.setInicio(inicio);
        dto.setFin(fin);
        dto.setClase(clase);
        dto.setEntrada(entrada);
        dto.setCiudad(ciudad);
        dto.setIdOrganizador(idOrganizador);
        if (imagen != null && !imagen.isEmpty()) {
            byte[] bytes = imagen.getBytes();
            Blob imageBlob = new javax.sql.rowset.serial.SerialBlob(bytes);
            dto.setImagen(imageBlob);
        } else {
            dto.setImagen(null);
        }

        EventoDTO evento = EventoMapper.toDTO(eventoService.createEvento(dto, idOrganizador));
        return ResponseEntity.created(URI.create("/eventos/" + evento.getIdEvento())).body(evento);
    }



    @PutMapping("/{idUsuario}/{idEvento}")
    public ResponseEntity<EventoDTO> actualizarEvento(
            @RequestBody EventoDTO entity,
            @PathVariable Long idUsuario,
            @PathVariable Long idEvento) {
        Evento evento = eventoService.updateEvento(idEvento, entity, idUsuario);
        return ResponseEntity.ok(EventoMapper.toDTO(evento));
    }

    @PostMapping("/{idEvento}/notificar")
    public ResponseEntity<String> notificarSeguidores(
            @PathVariable Long idEvento,
            @RequestBody String mensaje) {
        notificacionService.enviarNotificacion(idEvento, mensaje);
        return ResponseEntity.ok("Notificacion enviada");
    }

    @GetMapping("")
    public ResponseEntity<Page<EventoDTO>> getEventos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page >= 0 && size >= 0) {
            Page<Evento> eventoPage = eventoService.getEventos(page, size);
            Page<EventoDTO> dtoPage = eventoPage.map(EventoMapper::toDTO);
            return ResponseEntity.ok(dtoPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{idEvento}")
    public ResponseEntity<EventoDTO> getEventoById(@PathVariable Long idEvento) {
        Evento evento = eventoService.getEventoById(idEvento);
        return ResponseEntity.ok(EventoMapper.toDTO(evento));
    }

    @GetMapping("/clase/{clase}")
    public ResponseEntity<Page<EventoDTO>> getEventosByClase(
            @PathVariable String clase,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page >= 0 && size >= 0) {
            Page<Evento> eventoPage = eventoService.getEventosByClase(clase, page, size);
            Page<EventoDTO> dtoPage = eventoPage.map(EventoMapper::toDTO);
            return ResponseEntity.ok(dtoPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<Page<EventoDTO>> getEventosByCiudad(
            @PathVariable String ciudad,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page >= 0 && size >= 0) {
            Page<Evento> eventoPage = eventoService.getEventosByCiudad(ciudad, page, size);
            Page<EventoDTO> dtoPage = eventoPage.map(EventoMapper::toDTO);
            return ResponseEntity.ok(dtoPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/precio/{precio}")
    public ResponseEntity<Page<EventoDTO>> getEventosByPrecio(
            @PathVariable int precio,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page >= 0 && size >= 0) {
            Page<Evento> eventoPage = eventoService.getEventosByPrecio(precio, page, size);
            Page<EventoDTO> dtoPage = eventoPage.map(EventoMapper::toDTO);
            return ResponseEntity.ok(dtoPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/filtrar")
    public ResponseEntity<Page<EventoDTO>> getMethodName(@RequestBody EventoDTO filtro) {
        return ResponseEntity.ok(eventoService.getEventosByFilter(filtro)
            .map(EventoMapper::toDTO));
    }
    

    @DeleteMapping ("/{idEvento}")
    public ResponseEntity<Boolean> eliminarEvento(@PathVariable Long idEvento) {
        Boolean isElim = eventoService.deleteEvento(idEvento);
        return ResponseEntity.ok(isElim);
    }
    @GetMapping("/{idEvento}/seguidores")
    public ResponseEntity<List<UsuarioDTO>> getSeguidoresByEvento(@PathVariable Long idEvento) {
        List<UsuarioDTO> seguidores = eventoService.getSeguidoresByEvento(idEvento)
            .stream()
            .map(UsuarioMapper::toDTO)
            .toList();
        return ResponseEntity.ok(seguidores);
    }

    @GetMapping("/populares")
    public ResponseEntity<List<EventoDTO>> getMethodName() {
        List<EventoDTO> eventosPopulares = eventoService.getEventosPopulares().stream()
            .map(EventoMapper::toDTO)
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(eventosPopulares);
    }

    @GetMapping("/buscar/{nombreEvento}")
    public ResponseEntity<List<EventoDTO>> buscarPorNombre(@PathVariable String nombreEvento) {
        List<Evento> eventos = eventoService.getEventosByName(nombreEvento);
        return ResponseEntity.ok(
            eventos.stream().map(EventoMapper::toDTO).toList()
        );
    }

}
