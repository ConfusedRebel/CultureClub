package com.cultureclub.cclub.service.Int;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.EventoDTO;

public interface EventoService {

    public Page<Evento> getEventos(int page, int size);

    public Evento createEvento(EventoDTO entity, Long idUsuario) throws Exception;

    public Evento getEventoById(Long idEvento);

    public Evento updateEvento(Long idEvento, EventoDTO entity, Long idUsuario);

    public Page<Evento> getEventosByClase(String clase, int page, int size);

    public Page<Evento> getEventosByCiudad(String ciudad, int page, int size);

    public Page<Evento> getEventosByPrecio(int precio, int page, int size);

    public Boolean deleteEvento(Long idEvento);

    public List<Evento> getEventosPopulares();

    public List<Usuario> getSeguidoresByEvento(Long idEvento);

    public Page<Evento> getEventosByFilter(EventoDTO filtro);

    public List<Evento> getEventosByName(String param);

}
