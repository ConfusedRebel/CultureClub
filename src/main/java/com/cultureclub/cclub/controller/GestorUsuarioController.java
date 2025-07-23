package com.cultureclub.cclub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.mapper.UsuarioMapper;
import com.cultureclub.cclub.service.Int.GestorUsuarioService;

@RestController
@RequestMapping("/gestorUsuarios")
public class GestorUsuarioController {

    @Autowired
    private GestorUsuarioService gestorUsuarioService;


    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createUsuario(
        @RequestPart("usuario") UsuarioDTO usuario,
        @RequestPart(value = "foto", required = false) MultipartFile imagen
    ) {
        usuario.setImagen(imagen); // setea la imagen en el DTO si viene
        return ResponseEntity.created(gestorUsuarioService.createUsuario(usuario)).build();
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