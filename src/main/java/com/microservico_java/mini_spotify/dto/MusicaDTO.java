package com.microservico_java.mini_spotify.dto;

import com.microservico_java.mini_spotify.model.Musica;

public record MusicaDTO(
        Long id,
        String titulo,
        String artista,
        int duracaoSegundos,
        String nomeAlbum,
        String nomeGenero
) {
    public MusicaDTO(Musica musica) {
        this(
                musica.getId(),
                musica.getTitulo(),
                musica.getArtista(),
                musica.getDuracaoSegundos(),
                musica.getAlbum().getNome(),
                musica.getGenero().getNome()
        );
    }
}