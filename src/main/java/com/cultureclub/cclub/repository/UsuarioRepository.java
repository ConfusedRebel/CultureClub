package com.cultureclub.cclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cultureclub.cclub.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Find by username (returns a list in case there are multiple matches)
    List<Usuario> findByNombre(String nombre);

    Optional<Usuario> findByEmail(String email);

    // Additional methods can be added as needed

}
