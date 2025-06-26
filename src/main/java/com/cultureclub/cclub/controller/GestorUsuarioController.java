package com.cultureclub.cclub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.mapper.UsuarioMapper;
import com.cultureclub.cclub.service.Int.GestorUsuarioService;

@RestController
@RequestMapping("/gestorUsuarios")
public class GestorUsuarioController {

    @Autowired
    private GestorUsuarioService gestorUsuarioService;


    @PostMapping("")
    public ResponseEntity<Object> createUsuario(@RequestBody UsuarioDTO param) {
        return ResponseEntity.created(gestorUsuarioService.createUsuario(param)).build();
    }

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

    @PutMapping("rol")
    public ResponseEntity<UsuarioDTO> updateUsuarioRol(@RequestBody UsuarioDTO param) {
        return ResponseEntity.ok(UsuarioMapper.toDTO(gestorUsuarioService.updateUsuarioRol(param)));
    }
}