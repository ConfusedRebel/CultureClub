package com.cultureclub.cclub.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.service.GestorUsuarioService;

@RestController
@RequestMapping("/gestorUsuarios")
public class GestorUsuarioController {

    @Autowired
    private GestorUsuarioService usuarioService;

    @PostMapping("")
    public ResponseEntity<Object> postMethodName(@RequestBody UsuarioDTO entity) throws Exception {
        System.out.println("Creando usuario: " + entity.getEmail());
        Usuario usuario = usuarioService.createUsuario(entity);
        return ResponseEntity.created(URI.create("/usuarios/" + usuario.getIdUsuario())).body(usuario);
    }

    @GetMapping("")
    public ResponseEntity<Object> getMethodName(@RequestParam UsuarioDTO param) {
        return ResponseEntity.ok(usuarioService.getUsuario(param));
    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteUsuario(@RequestParam UsuarioDTO param) {
        usuarioService.deleteUsuario(param);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/premium")
    public ResponseEntity<Object> getPremiumUsuarios(
            @RequestParam(required = false, defaultValue = "false") boolean isPremium) {
        return ResponseEntity.ok(usuarioService.getPremiumUsuarios());
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping("/login")
    public ResponseEntity<Object> login(@RequestParam String email, @RequestParam String password) {
        Usuario usuario = usuarioService.login(email, password);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}