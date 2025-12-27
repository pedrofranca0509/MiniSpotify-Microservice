package com.microservico_java.mini_spotify.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlaylistRequestDTO(
        @NotBlank String nome,
        @NotNull Long usuarioId,
        List<Long> musicasIds // lista de IDs das músicas
) {}