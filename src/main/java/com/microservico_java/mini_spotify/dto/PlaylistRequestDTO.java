package com.microservico_java.mini_spotify.dto;

import jakarta.validation.constraints.NotBlank;

public record PlaylistRequestDTO(
        @NotBlank String nome,
        Long usuarioId
) {}