package com.cultureclub.cclub.security;


import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.dto.UsuarioDTO;
import com.cultureclub.cclub.entity.enumeradores.Ciudad;
import com.cultureclub.cclub.entity.enumeradores.Rol;
import com.cultureclub.cclub.entity.exceptions.UsuarioDuplicateException;
import com.cultureclub.cclub.repository.UsuarioRepository;

@Service
public class AuthService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    public String login(String email, String password) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Credenciales inválidas");
        }

        var userDetails = userDetailsService.loadUserByUsername(email);
        return jwtUtil.generateToken(userDetails);
    }


    public String register(UsuarioDTO usuario) throws UsuarioDuplicateException {
        System.out.println("Creando usuario: " + usuario.getEmail());
        Optional<Usuario> oldusuario = usuarioRepository.findByEmail(usuario.getEmail());
        if (oldusuario.isPresent()) {
            throw new UsuarioDuplicateException("El usuario con el email " + usuario.getEmail() + " ya existe.");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setNombre(usuario.getNombre());
        newUsuario.setEmail(usuario.getEmail());
        newUsuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        newUsuario.setApellidos(usuario.getApellidos());
        newUsuario.setCiudad(Ciudad.valueOf(usuario.getCiudad()));
        newUsuario.setTelefono(usuario.getTelefono());
        newUsuario.setRoles(usuario.getRoles() != null ? usuario.getRoles() : Set.of(Rol.USUARIO));

        usuarioRepository.save(newUsuario); // ✅ GUARDAR PRIMERO

        // ✅ Cargar con email, no nombre
        var userDetails = userDetailsService.loadUserByUsername(newUsuario.getEmail());
        return jwtUtil.generateToken(userDetails);
    }
}
