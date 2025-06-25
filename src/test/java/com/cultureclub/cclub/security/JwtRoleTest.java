package com.cultureclub.cclub.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.enumeradores.Rol;

class JwtRoleTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    void tokenContainsRoleAdministrador() {
        Usuario admin = new Usuario();
        admin.setIdUsuario(1L);
        admin.setEmail("admin@example.com");
        admin.setPassword("secret");
        admin.setRoles(Set.of(Rol.ADMINISTRADOR));

        UsuarioDetails userDetails = new UsuarioDetails(admin);
        String token = jwtUtil.generateToken(userDetails);

        assertTrue(jwtUtil.extractRoles(token).contains("ROLE_ADMINISTRADOR"));
        assertEquals(1L, jwtUtil.extractUserId(token));
    }
}
