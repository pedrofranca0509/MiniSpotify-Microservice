package com.microservico_java.mini_spotify.service;

import com.microservico_java.mini_spotify.dto.MusicaDTO;
import com.microservico_java.mini_spotify.dto.MusicaRequestDTO;
import com.microservico_java.mini_spotify.model.Album;
import com.microservico_java.mini_spotify.model.Genero;
import com.microservico_java.mini_spotify.model.Musica;
import com.microservico_java.mini_spotify.repository.AlbumRepository;
import com.microservico_java.mini_spotify.repository.GeneroRepository;
import com.microservico_java.mini_spotify.repository.MusicaRepository;
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
    private final AlbumRepository albumRepository;

    public List<MusicaDTO> listarTodas() {
        return musicaRepository.findAll().stream()
                .map(MusicaDTO::new)
                .toList();
    }

    public MusicaDTO buscarPorId(Long id) {
        Musica musica = musicaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada"));
        return new MusicaDTO(musica);
    }

    public List<MusicaDTO> buscarPorTitulo(String titulo) {
        return musicaRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(MusicaDTO::new)
                .toList();
    }

    public MusicaDTO salvar(MusicaRequestDTO dto) {
        Musica musica = new Musica();
        musica.setTitulo(dto.titulo());
        musica.setArtista(dto.artista());
        musica.setDuracaoSegundos(dto.duracaoSegundos());
        musica.setGenero(buscarGenero(dto.generoId()));
        musica.setAlbum(buscarAlbum(dto.albumId()));

        Musica salva = musicaRepository.save(musica);
        return new MusicaDTO(salva);
    }

    public MusicaDTO atualizar(Long id, MusicaRequestDTO dto) {
        Musica existente = musicaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada"));

        existente.setTitulo(dto.titulo());
        existente.setGenero(buscarGenero(dto.generoId()));
        existente.setAlbum(buscarAlbum(dto.albumId()));

        Musica atualizada = musicaRepository.save(existente);
        return new MusicaDTO(atualizada);
    }

    public void deletar(Long id) {
        if (!musicaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada");
        }
        musicaRepository.deleteById(id);
    }

    private Genero buscarGenero(Long id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gênero não encontrado"));
    }

    private Album buscarAlbum(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Álbum não encontrado"));
    }
}
