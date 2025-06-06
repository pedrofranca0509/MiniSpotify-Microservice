package com.microservico_java.mini_spotify.service;

import com.microservico_java.mini_spotify.dto.PlaylistRequestDTO;
import com.microservico_java.mini_spotify.dto.PlaylistResponseDTO;
import com.microservico_java.mini_spotify.model.Playlist;
import com.microservico_java.mini_spotify.model.Usuario;
import com.microservico_java.mini_spotify.repository.PlaylistRepository;
import com.microservico_java.mini_spotify.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UsuarioRepository usuarioRepository;

    public PlaylistResponseDTO criarPlaylist(PlaylistRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário com ID " + request.usuarioId() + " não encontrado."
                ));

        Playlist playlist = new Playlist();
        playlist.setNome(request.nome());
        playlist.setUsuario(usuario);
        playlist = playlistRepository.save(playlist);

        return new PlaylistResponseDTO(playlist);
    }

    public List<PlaylistResponseDTO> listarTodas() {
        return playlistRepository.findAll().stream()
                .map(PlaylistResponseDTO::new)
                .toList();
    }
}
