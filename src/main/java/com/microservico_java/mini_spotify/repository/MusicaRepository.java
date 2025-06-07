package com.microservico_java.mini_spotify.repository;

import com.microservico_java.mini_spotify.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
    List<Musica> findByTituloContainingIgnoreCase(String titulo);
    List<Musica> findByGeneroId(Long generoId);
    List<Musica> findByArtistaContainingIgnoreCase(String artista);
    Optional<Musica> findByTitulo(String titulo);
}