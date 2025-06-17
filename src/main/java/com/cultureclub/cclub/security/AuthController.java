package com.cultureclub.cclub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.dto.Authentication.AuthRequestDTO;
import com.cultureclub.cclub.entity.dto.Authentication.AuthResponseDTO;
import com.cultureclub.cclub.entity.dto.Authentication.ErrorResponseDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest) {
        String token = authService.login(authRequest.getEmail(), authRequest.getPassword());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioDTO request) {
        try {
            String token = authService.register(request);
            return ResponseEntity.ok(new AuthResponseDTO(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponseDTO("Error: " + e.getMessage()));
        }
    }
}
