package com.microservico_java.mini_spotify.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MusicaRequestDTO(
        @NotBlank String titulo,
        @NotNull Long generoId,
        @NotNull Long albumId
) {}
