package com.cultureclub.cclub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cultureclub.cclub.entity.Usuario;
import com.cultureclub.cclub.entity.enumeradores.Rol;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Find by name (returns a list in case there are multiple matches)
    List<Usuario> findByNombre(String nombre);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRolesContaining(Rol rol);
    List<Usuario> findByRolesNotContaining(Rol rol);

    // Additional methods can be added as needed

    public List<Usuario> findBySeguidos_IdUsuario(Long usuarioId);

    public List<Usuario> findBySeguidores_IdUsuario(Long idUsuario);

    public Optional<Usuario> findByEmailAndPassword(String email, String password);

}
