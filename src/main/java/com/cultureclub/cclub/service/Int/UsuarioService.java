package com.cultureclub.cclub.service.Int;

import java.util.List;
import java.util.Optional;

import com.cultureclub.cclub.entity.Entrada;
import com.cultureclub.cclub.entity.EventoAsistido;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.EntradaDTO;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.dto.reporte.ReporteDTO;
import com.cultureclub.cclub.entity.reportes.Reporte;

public interface UsuarioService {

    Optional<Usuario> getUsuarioById(Long id);

    String updateUsuario(Long id, UsuarioDTO data);

    Reporte reportarUsuario(ReporteDTO reporte) throws Exception;

    void calificarEvento(Long idUsuario, Long idEvento, int calificacion);

    Optional<Entrada> comprarEntrada(Long idUsuario, EntradaDTO entradaDTO);

    void seguirUsuario(Long usuarioId, Long usuarioSeguidoId);

    void seguirEvento(Long usuarioId, Long eventoId);

    List<EventoAsistido> getEventosAsistidos(Long usuarioId);

    public List<Usuario> getSeguidoresByUsuario(Long usuarioId);

    public List<Entrada> getEntradas(Long idUsuario);

    List<Usuario> getSeguidosByUsuario(Long usuarioId);

    public Optional<Usuario> login(String email, String password);

}
