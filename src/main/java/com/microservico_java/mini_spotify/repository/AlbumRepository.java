package com.microservico_java.mini_spotify.repository;

import com.microservico_java.mini_spotify.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByNomeContainingIgnoreCase(String nome);
    List<Album> findByArtistaContainingIgnoreCase(String artista);
}