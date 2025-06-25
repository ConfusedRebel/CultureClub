package com.cultureclub.cclub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.service.Int.GestorUsuarioService;

@RestController
@RequestMapping("/gestorUsuarios")
public class GestorUsuarioController {

    @Autowired
    private GestorUsuarioService gestorUsuarioService;

    @GetMapping("")
    public ResponseEntity<Object> getUsuarioBy_Id_Email(@RequestBody UsuarioDTO param) {
        return ResponseEntity.ok(gestorUsuarioService.getUsuario(param));
    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteUsuario(@RequestBody UsuarioDTO param) {
        gestorUsuarioService.deleteUsuario(param);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/premium")
    public ResponseEntity<Object> getPremiumUsuarios(
            @RequestParam(required = false, defaultValue = "true") boolean isPremium) {
        return ResponseEntity.ok(gestorUsuarioService.getPremiumUsuarios(isPremium));
    }

    @GetMapping("/admin")
    public ResponseEntity<Object> getAdmingUsuarios(
            @RequestParam(required = false, defaultValue = "true") boolean isAdmin) {
        return ResponseEntity.ok(gestorUsuarioService.getAdminUsuarios(isAdmin));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsuarios() {
        return ResponseEntity.ok(gestorUsuarioService.getAllUsuarios());
    }
}