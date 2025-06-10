package com.microservico_java.mini_spotify.service;

import com.microservico_java.mini_spotify.dto.MusicaResponseDTO;
import com.microservico_java.mini_spotify.dto.MusicaRequestDTO;
import com.microservico_java.mini_spotify.model.Genero;
import com.microservico_java.mini_spotify.model.Musica;
import com.microservico_java.mini_spotify.model.Playlist;
import com.microservico_java.mini_spotify.repository.GeneroRepository;
import com.microservico_java.mini_spotify.repository.MusicaRepository;
import com.microservico_java.mini_spotify.repository.PlaylistRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicaService {

    private final MusicaRepository musicaRepository;
    private final GeneroRepository generoRepository;
    private final PlaylistRepository playlistRepository;

    public List<MusicaResponseDTO> listarTodas() {
        return musicaRepository.findAll().stream()
                .map(MusicaResponseDTO::new)
                .toList();
    }

    public MusicaResponseDTO buscarPorId(Long id) {
        Musica musica = musicaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada"));
        return new MusicaResponseDTO(musica);
    }

    public List<MusicaResponseDTO> buscarPorTitulo(String titulo) {
        return musicaRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(MusicaResponseDTO::new)
                .toList();
    }

    public MusicaResponseDTO salvar(MusicaRequestDTO dto) {
        Musica musica = new Musica();
        musica.setTitulo(dto.titulo());
        musica.setArtista(dto.artista());
        musica.setDuracaoSegundos(dto.duracaoSegundos());
        musica.setGenero(buscarGenero(dto.generoId()));

        Musica salva = musicaRepository.save(musica);
        return new MusicaResponseDTO(salva);
    }

    public MusicaResponseDTO atualizar(Long id, MusicaRequestDTO dto) {
        Musica existente = musicaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada"));

        existente.setTitulo(dto.titulo());
        existente.setGenero(buscarGenero(dto.generoId()));

        Musica atualizada = musicaRepository.save(existente);
        return new MusicaResponseDTO(atualizada);
    }

    @Transactional
    public void deletar(Long id) {
        /**if (!musicaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada");
        }
        musicaRepository.deleteById(id);8*/

         Musica musica = musicaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada"));

    // Remove a música de todas as playlists
    List<Playlist> playlists = playlistRepository.findAllByMusicaId(id);
    for (Playlist playlist : playlists) {
        if (playlist.getMusicas().contains(musica)) {
            playlist.getMusicas().remove(musica);
        }
    }

    playlistRepository.saveAll(playlists); // Atualiza as playlists
    musicaRepository.delete(musica); // Deleta a música
    }

    private Genero buscarGenero(Long id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gênero não encontrado"));
    }
}
