package com.microservico_java.mini_spotify.service;

import com.microservico_java.mini_spotify.dto.PlaylistRequestDTO;
import com.microservico_java.mini_spotify.model.*;
import com.microservico_java.mini_spotify.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UsuarioRepository usuarioRepository;
    private final MusicaRepository musicaRepository;

    // No seu PlaylistService.java, modifique o método:
    public Playlist criarPlaylist(PlaylistRequestDTO request) {
        // Adicione tratamento de erro:
        try {
            Usuario usuario = usuarioRepository.findById(request.usuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

            Playlist playlist = new Playlist();
            playlist.setNome(request.nome());
            playlist.setUsuario(usuario);
            return playlistRepository.save(playlist);

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao criar playlist: " + e.getMessage()
            );
        }
    }

    public List<Playlist> listarTodas() {
        return playlistRepository.findAll();
    }
}