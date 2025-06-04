package com.microservico_java.mini_spotify.dto;

import com.microservico_java.mini_spotify.model.Musica;

public record MusicaResponseDTO(
        Long id,
        String titulo,
        String artista,
        int duracaoSegundos,
        String nomeGenero
) {
    public MusicaResponseDTO (Musica musica) {
        this(
                musica.getId(),
                musica.getTitulo(),
                musica.getArtista(),
                musica.getDuracaoSegundos() != null ? musica.getDuracaoSegundos() : 0,
                musica.getGenero() != null ? musica.getGenero().getNome() : null
        );
    }
}