package com.microservico_java.mini_spotify.dto;

import com.microservico_java.mini_spotify.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
