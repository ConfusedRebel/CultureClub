package com.cultureclub.cclub.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cultureclub.cclub.entity.Entrada;
import com.cultureclub.cclub.entity.Evento;
import com.cultureclub.cclub.entity.Resena;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.ResenaDTO;
import com.cultureclub.cclub.mapper.ResenaMapper;
import com.cultureclub.cclub.repository.EventoRepository;
import com.cultureclub.cclub.repository.ResenaRepository;
import com.cultureclub.cclub.repository.UsuarioRepository;
import com.cultureclub.cclub.service.Int.ResenaService;

@Service
public class ResenaServiceImpl implements ResenaService {
    @Autowired
    private ResenaRepository resenaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public Resena agregarResena(Long idUsuario, Long idEvento, ResenaDTO resenaDTO) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado"));
        Resena resena = ResenaMapper.toEntity(resenaDTO, evento, usuario);
        return resenaRepository.save(resena);
    }

    @Override
    public List<Resena> obtenerResenasEvento(Long idEvento) {
        return resenaRepository.findByEvento_IdEvento(idEvento);
    }

    @Override
    public List<Resena> obtenerResenasUsuario(Long idUsuario) {
        return resenaRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @Override
    public Resena actualizarResena(Long idResena, ResenaDTO resenaDTO) {
        Optional<Resena> optionalResena = resenaRepository.findById(idResena);
        if (optionalResena.isPresent()) {
            Resena resena = optionalResena.get();
            ResenaMapper.updateEntityIfPresent(resena, resenaDTO);
            return resenaRepository.save(resena);
        } else {
            throw new IllegalArgumentException("Reseña no encontrada con ID: " + idResena);
        }
    }

    @Override
    public void eliminarResena(Long idResena) {
        resenaRepository.deleteById(idResena);
    }
}
