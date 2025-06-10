package com.microservico_java.mini_spotify.repository;

import com.microservico_java.mini_spotify.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

     @Query("SELECT DISTINCT p FROM Playlist p LEFT JOIN FETCH p.musicas LEFT JOIN FETCH p.usuario")
    List<Playlist> findAllWithMusicasAndUsuario();

    boolean existsByNomeAndUsuario(String nome, com.microservico_java.mini_spotify.model.Usuario usuario);

}