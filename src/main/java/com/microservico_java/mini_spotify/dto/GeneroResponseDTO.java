package com.microservico_java.mini_spotify.dto;

import com.microservico_java.mini_spotify.model.Genero;

public record GeneroResponseDTO(
    Long id,
    String nome
) {
    public GeneroResponseDTO(Genero genero) {
        this(genero.getId(), genero.getNome());
    }
}
