package com.cultureclub.cclub.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("")
    public ResponseEntity<Object> postMethodName(@RequestBody UsuarioDTO entity) throws Exception {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHH");
        System.out.println("Creando usuario: " + entity.getEmail());
        Usuario usuario = usuarioService.createUsuario(entity);
        return ResponseEntity.created(URI.create("/usuarios/" + usuario.getIdUsuario())).body(usuario);
    }

}
