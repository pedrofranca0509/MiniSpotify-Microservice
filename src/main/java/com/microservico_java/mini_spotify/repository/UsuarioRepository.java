package com.microservico_java.mini_spotify.repository;

import com.microservico_java.mini_spotify.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca usuário por email (para login)
    Optional<Usuario> findByEmail(String email);

    // Verifica se email já existe
    boolean existsByEmail(String email);
}