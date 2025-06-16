package com.cultureclub.cclub.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cultureclub.cclub.entity.Ciudad;
import com.cultureclub.cclub.entity.Entrada;
import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.TipoEntrada;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.EntradaDTO;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.dto.reporte.ReporteDTO;
import com.cultureclub.cclub.entity.reportes.Reporte;

import com.cultureclub.cclub.repository.UsuarioRepository;
import com.mapper.ReporteMapper;

import jakarta.transaction.Transactional;

import com.cultureclub.cclub.repository.EventoRepository;
import com.cultureclub.cclub.repository.ReporteErrorRepository;
import com.cultureclub.cclub.repository.ReporteEventoRepository;
import com.cultureclub.cclub.repository.ReporteUsuarioRepository;
import com.cultureclub.cclub.repository.EntradaRepository;

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
        Reporte reporteEntity = ReporteMapper.toEntity(reporte, usuarioEmisor.get());

        if (reporteEntity instanceof com.cultureclub.cclub.entity.reportes.ReporteError error) {
            return reporteErrorRepository.save(error);
        } else if (reporteEntity instanceof com.cultureclub.cclub.entity.reportes.ReporteEvento evento) {
            return reporteEventoRepository.save(evento);
        } else if (reporteEntity instanceof com.cultureclub.cclub.entity.reportes.ReporteUsuario usuario) {
            return reporteUsuarioRepository.save(usuario);
        } else {
            throw new IllegalArgumentException("Tipo de reporte no soportado");
        }
    }

    @Override
    public Usuario login(String email, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            Usuario foundUser = usuario.get();
            if (foundUser.getPassword().equals(password)) {
                return foundUser; // Login successful
            } else {
                throw new IllegalArgumentException("Contraseña incorrecta para el usuario: " + email);
            }
        } else {
            throw new IllegalArgumentException("Usuario no encontrado con email: " + email);
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
            System.out.println("El usuario " + usuario.getNombre() + " ya sigue a " + usuarioSeguido.getNombre());
        }
    }
}
