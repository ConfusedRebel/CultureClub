package com.cultureclub.cclub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultureclub.cclub.entity.Resena;
import com.cultureclub.cclub.entity.dto.ResenaDTO;
import com.cultureclub.cclub.mapper.ResenaMapper;
import com.cultureclub.cclub.service.Int.ResenaService;

@RestController
@RequestMapping("/resenas")
public class ResenaController {
    @Autowired
    private ResenaService resenaService;

    @PostMapping("/{idUsuario}/{idEvento}")
    public ResponseEntity<ResenaDTO> crearResena(
            @PathVariable Long idUsuario,
            @PathVariable Long idEvento,
            @RequestBody ResenaDTO resenaDTO) {
        Resena resena = resenaService.agregarResena(idUsuario, idEvento, resenaDTO);
        return ResponseEntity.ok(ResenaMapper.toDTO(resena));
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<ResenaDTO>> getResenasEvento(@PathVariable Long idEvento) {
        List<ResenaDTO> dtos = resenaService.obtenerResenasEvento(idEvento)
                .stream().map(ResenaMapper::toDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ResenaDTO>> getResenasUsuario(@PathVariable Long idUsuario) {
        List<ResenaDTO> dtos = resenaService.obtenerResenasUsuario(idUsuario)
                .stream().map(ResenaMapper::toDTO).toList();
        return ResponseEntity.ok(dtos);
    }
}
