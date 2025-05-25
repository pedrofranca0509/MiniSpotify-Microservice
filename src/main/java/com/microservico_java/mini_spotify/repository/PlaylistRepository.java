package com.microservico_java.mini_spotify.repository;

import com.microservico_java.mini_spotify.model.Playlist;
import com.microservico_java.mini_spotify.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    // Lista playlists de um usuário
    List<Playlist> findByUsuario(Usuario usuario);

    // Busca por nome (ignorando maiúsculas/minúsculas)
    List<Playlist> findByNomeContainingIgnoreCase(String nome);
}