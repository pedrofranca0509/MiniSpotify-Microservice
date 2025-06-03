package com.microservico_java.mini_spotify.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.microservico_java.mini_spotify.model.Playlist;

public record PlaylistResponseDTO(
        Long id,
        String nome,
        String nomeUsuario,
        List<MusicaDTO> musicas
) {
    public PlaylistResponseDTO(Playlist playlist) {
        this(
            playlist.getId(),
            playlist.getNome(),
            playlist.getUsuario().getNome(),
             playlist.getMusicas()
                    .stream()
                    .map(MusicaDTO::new)
                    .collect(Collectors.toList())
        );
    }
}
