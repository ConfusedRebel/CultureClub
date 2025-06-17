package com.cultureclub.cclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.enumeradores.Rol;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Find by name (returns a list in case there are multiple matches)
    List<Usuario> findByNombre(String nombre);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRolesContaining(Rol rol);
    List<Usuario> findByRolesNotContaining(Rol rol);

    // Additional methods can be added as needed

}
