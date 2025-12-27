package com.microservico_java.mini_spotify.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MusicaRequestDTO(
        @NotBlank String titulo,
        @NotBlank String artista,
        @Min(1) int duracaoSegundos,
        @NotNull Long generoId
) {}
