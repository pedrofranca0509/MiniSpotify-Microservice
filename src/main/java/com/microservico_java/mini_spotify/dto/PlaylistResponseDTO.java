package com.microservico_java.mini_spotify.dto;

import java.util.List;

import com.microservico_java.mini_spotify.model.Playlist;

public record PlaylistResponseDTO(
        Long id,
        String nome,
        String nomeUsuario,
        List<MusicaResponseDTO> musicas
) {
    public PlaylistResponseDTO(Playlist playlist) {
        this(
            playlist.getId(),
            playlist.getNome(),
            playlist.getUsuario() != null ? playlist.getUsuario().getNome() : null,
            playlist.getMusicas() != null ? 
                playlist.getMusicas().stream()
                    .map(MusicaResponseDTO::new)
                    .toList() 
                : List.of()
        );
    }
}
