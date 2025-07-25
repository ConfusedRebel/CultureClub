package com.cultureclub.cclub.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cultureclub.cclub.entity.Entrada;
import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.EventoAsistido;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.EntradaDTO;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.dto.reporte.ReporteDTO;
import com.cultureclub.cclub.entity.enumeradores.Ciudad;
import com.cultureclub.cclub.entity.enumeradores.TipoEntrada;
import com.cultureclub.cclub.entity.reportes.Reporte;
import com.cultureclub.cclub.mapper.ReporteMapper;
import com.cultureclub.cclub.repository.EntradaRepository;
import com.cultureclub.cclub.repository.EventoRepository;
import com.cultureclub.cclub.repository.ReporteErrorRepository;
import com.cultureclub.cclub.repository.ReporteEventoRepository;
import com.cultureclub.cclub.repository.ReporteUsuarioRepository;
import com.cultureclub.cclub.repository.UsuarioRepository;
import com.cultureclub.cclub.service.Int.UsuarioService;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ReporteErrorRepository reporteErrorRepository;

    @Autowired
    private ReporteEventoRepository reporteEventoRepository;

    @Autowired
    private ReporteUsuarioRepository reporteUsuarioRepository;
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private EntradaRepository entradaRepository;

    @Override
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public String updateUsuario(Long id, UsuarioDTO data) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setNombre(!data.getNombre().isEmpty() ? data.getNombre() : usuario.getNombre());
        usuario.setEmail(!data.getEmail().isEmpty() ? data.getEmail() : usuario.getEmail());
        usuario.setApellidos(!data.getApellidos().isEmpty() ? data.getApellidos() : usuario.getApellidos());
        usuario.setCiudad(!data.getCiudad().equals("") ? Enum.valueOf(Ciudad.class, data.getCiudad().toUpperCase()) : usuario.getCiudad());
        if (data.getTelefono() != 0){
            usuario.setTelefono(data.getTelefono());
        }
        usuario.setPassword(!"".equals(data.getPassword()) ? data.getPassword() : usuario.getPassword());
        usuario.setFoto(data.getFotoBlob() != null ? data.getFotoBlob() : usuario.getFoto());
        usuarioRepository.save(usuario);
        return "Usuario actualizado correctamente";
    }

    @Override
    public Reporte reportarUsuario(ReporteDTO reporte) {
        Optional<Usuario> usuarioEmisor = usuarioRepository.findById(reporte.getIdEmisor());
        if (usuarioEmisor.isEmpty()) {
            throw new IllegalArgumentException("Usuario emisor no encontrado con ID: " + reporte.getIdEmisor());
        }
        Reporte reporteEntity = ReporteMapper.toEntity(reporte, usuarioEmisor.get());

        if (reporteEntity instanceof com.cultureclub.cclub.entity.reportes.ReporteError) {
            return reporteErrorRepository.save((com.cultureclub.cclub.entity.reportes.ReporteError) reporteEntity);
        } else if (reporteEntity instanceof com.cultureclub.cclub.entity.reportes.ReporteEvento) {
            return reporteEventoRepository.save((com.cultureclub.cclub.entity.reportes.ReporteEvento) reporteEntity);
        } else if (reporteEntity instanceof com.cultureclub.cclub.entity.reportes.ReporteUsuario) {
            return reporteUsuarioRepository.save((com.cultureclub.cclub.entity.reportes.ReporteUsuario) reporteEntity);
        } else {
            throw new IllegalArgumentException("Tipo de reporte no soportado");
        }
    }

    @Override
    public void calificarEvento(Long idUsuario, Long idEvento, int calificacion) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + idUsuario);
        }
        Optional<Evento> eventoOpt = eventoRepository.findById(idEvento);
        if (eventoOpt.isEmpty()) {
            throw new IllegalArgumentException("Evento no encontrado con ID: " + idEvento);
        }
        Usuario usuario = usuarioOpt.get();
        Evento evento = eventoOpt.get();

        // Initialize values to zero if they are null
        if (evento.getCalificacion() == null) {
            evento.setCalificacion(0);
        }
        if (evento.getCantidadVisitas() == null) {
            evento.setCantidadVisitas(0);
        }

        int visitasActuales = evento.getCantidadVisitas();
        int calificacionAcumulada = evento.getCalificacion() * visitasActuales;
        int calificacionTotalActual = calificacion + calificacionAcumulada;

        visitasActuales += 1;
        double promedio = (double) calificacionTotalActual / visitasActuales;

        evento.setCantidadVisitas(visitasActuales);
        evento.setCalificacion((int) Math.round(promedio));
        eventoRepository.save(evento);
        System.out
                .println("Usuario " + usuario.getNombre() + " calificó el evento " + idEvento + " con " + calificacion);
    }

    @Override
    @Transactional
    public Optional<Entrada> comprarEntrada(Long idUsuario, EntradaDTO entradaDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + idUsuario);
        }
        Usuario usuario = usuarioOpt.get();
        Optional<Evento> eventoOpt = eventoRepository.findById(entradaDTO.getIdEvento());
        if (eventoOpt.isEmpty()) {
            throw new IllegalArgumentException("Evento no encontrado con ID: " + entradaDTO.getIdEvento());
        }
        Evento evento = eventoOpt.get();

        Entrada entrada = new Entrada();
        entrada.setFechaUso(entradaDTO.getFechaUso());
        entrada.setTipoEntrada(TipoEntrada.valueOf(entradaDTO.getTipoEntrada()));
        entrada.setCompradorUsuario(usuario);
        entrada.setEvento(evento);
        entrada.setPrecioPagado(entradaDTO.getPrecioPagado());
        entrada.setFechaCompra(entradaDTO.getFechaCompra());

        usuario.getEntradas().add(entrada);
        evento.getEntradas().add(entrada);

        EventoAsistido asistido = new EventoAsistido();
        asistido.setNombreEvento(evento.getNombre());
        asistido.setFechaEvento(entradaDTO.getFechaUso());
        usuario.getEventosAsistidos().add(asistido);
        usuarioRepository.save(usuario);

        return Optional.ofNullable(entradaRepository.save(entrada));

    }

    @Override
    public void seguirUsuario(Long usuarioId, Long usuarioSeguidoId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId);
        }
        Optional<Usuario> usuarioSeguidoOpt = usuarioRepository.findById(usuarioSeguidoId);
        if (usuarioSeguidoOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario seguido no encontrado con ID: " + usuarioSeguidoId);
        }
        Usuario usuarioSeguido = usuarioSeguidoOpt.get();
        Usuario usuario = usuarioOpt.get();

        if (!usuario.getSeguidos().contains(usuarioSeguido)) {
            usuario.getSeguidos().add(usuarioSeguido);
            usuarioRepository.save(usuario);
            usuarioSeguido.getSeguidores().add(usuario);
            usuarioRepository.save(usuarioSeguido);
            System.out.println("Usuario " + usuario.getNombre() + " ahora sigue a " + usuarioSeguido.getNombre());
        } else {
            usuario.getSeguidos().remove(usuarioSeguido);
            usuarioSeguido.getSeguidores().remove(usuario);
            usuarioRepository.save(usuario);
            usuarioRepository.save(usuarioSeguido);
            System.out.println("Usuario " + usuario.getNombre() + " dejó de seguir a " + usuarioSeguido.getNombre());
        }
    }

    @Override
    public void seguirEvento(Long usuarioId, Long eventoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId));
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado con ID: " + eventoId));

        if (!evento.getSeguidores().contains(usuario)) {
            evento.getSeguidores().add(usuario);
            usuario.getEventosSeguidos().add(evento);
            eventoRepository.save(evento);
            usuarioRepository.save(usuario);
        }
    }

    @Override
    public java.util.List<EventoAsistido> getEventosAsistidos(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + usuarioId));
        return usuario.getEventosAsistidos();
    }

    @Override
    public List<Usuario> getSeguidoresByUsuario(Long usuarioId) {
    List<Usuario> seguidores = usuarioRepository.findBySeguidos_IdUsuario(usuarioId);
        return seguidores;
    }

    @Override
    public List<Entrada> getEntradas(Long idUsuario) {
        List<Entrada> entradas = entradaRepository.findByCompradorUsuario_IdUsuario(idUsuario);
        if (entradas.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron entradas para el usuario con ID: " + idUsuario);
        }
        return entradas;
    }

    @Override
    public List<Usuario> getSeguidosByUsuario(Long usuarioId) {
        List<Usuario> seguidos = usuarioRepository.findBySeguidores_IdUsuario(usuarioId);
        return seguidos;
    }

    @Override
public Optional<Usuario> login(String email, String password) {
    Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
    
    if (usuarioOpt.isEmpty()) {
        throw new IllegalArgumentException("Credenciales inválidas");
    }

    Usuario usuario = usuarioOpt.get();

    if (!usuario.getPassword().equals(password)) {
        throw new IllegalArgumentException("Credenciales inválidas");
    }

    return Optional.of(usuario);
}
}
