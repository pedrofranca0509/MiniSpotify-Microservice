package com.microservico_java.mini_spotify.service;

import com.microservico_java.mini_spotify.dto.PlaylistRequestDTO;
import com.microservico_java.mini_spotify.dto.PlaylistResponseDTO;
import com.microservico_java.mini_spotify.model.Musica;
import com.microservico_java.mini_spotify.model.Playlist;
import com.microservico_java.mini_spotify.model.Usuario;
import com.microservico_java.mini_spotify.repository.MusicaRepository;
import com.microservico_java.mini_spotify.repository.PlaylistRepository;
import com.microservico_java.mini_spotify.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UsuarioRepository usuarioRepository;
    private final MusicaRepository musicaRepository;


    public PlaylistResponseDTO criarPlaylist(PlaylistRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário com ID " + request.usuarioId() + " não encontrado."
                ));

        Playlist playlist = new Playlist();
        playlist.setNome(request.nome());
        playlist.setUsuario(usuario);

        if (request.musicasIds() != null && !request.musicasIds().isEmpty()) {
            List<Musica> musicas = musicaRepository.findAllById(request.musicasIds());
            if (musicas.size() != request.musicasIds().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Uma ou mais músicas não foram encontradas.");
            }
            playlist.setMusicas(new HashSet<>(musicas));  
        }

        playlist = playlistRepository.save(playlist);

        return new PlaylistResponseDTO(playlist);
    }

    public List<PlaylistResponseDTO> listarTodas() {
    log.debug("Listando todas as playlists");
        return playlistRepository.findAllWithMusicasAndUsuario().stream()
                .peek(p -> log.debug("Processando playlist: {}", p.getId()))
                .map(PlaylistResponseDTO::new)
                .toList();
}

        public PlaylistResponseDTO buscarPorId(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrada"));
        return new PlaylistResponseDTO(playlist);
    }

    public PlaylistResponseDTO atualizar(Long id, PlaylistRequestDTO request) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrada"));

        playlist.setNome(request.nome());

        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        playlist.setUsuario(usuario);

        if (request.musicasIds() != null && !request.musicasIds().isEmpty()) {
            List<Musica> musicas = musicaRepository.findAllById(request.musicasIds());
            playlist.setMusicas(new HashSet<>(musicas));
        }

        playlist = playlistRepository.save(playlist);
        return new PlaylistResponseDTO(playlist);
    }

    public void deletar(Long id) {
        if (!playlistRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist não encontrada");
        }
        playlistRepository.deleteById(id);
    }
}
