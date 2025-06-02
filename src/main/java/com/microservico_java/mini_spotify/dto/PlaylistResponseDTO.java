package com.microservico_java.mini_spotify.dto;

import com.microservico_java.mini_spotify.model.Playlist;

public record PlaylistResponseDTO(
        Long id,
        String nome,
        String nomeUsuario
) {
    public PlaylistResponseDTO(Playlist playlist) {
        this(
            playlist.getId(),
            playlist.getNome(),
            playlist.getUsuario().getNome()
        );
    }
}
