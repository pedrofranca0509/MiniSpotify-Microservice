package com.microservico_java.mini_spotify.service;

import com.microservico_java.mini_spotify.dto.GeneroRequestDTO;
import com.microservico_java.mini_spotify.dto.GeneroResponseDTO;
import com.microservico_java.mini_spotify.model.Genero;
import com.microservico_java.mini_spotify.repository.GeneroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    public GeneroResponseDTO criar(GeneroRequestDTO dto) {
        Genero genero = new Genero();
        genero.setNome(dto.nome());
        genero = generoRepository.save(genero);
        return new GeneroResponseDTO(genero);
    }

    public List<GeneroResponseDTO> listarTodos() {
        return generoRepository.findAll()
                .stream()
                .map(GeneroResponseDTO::new)
                .toList();
    }

    public GeneroResponseDTO buscarPorId(Long id) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gênero não encontrado com ID: " + id));
        return new GeneroResponseDTO(genero);
    }

    public GeneroResponseDTO atualizar(Long id, GeneroRequestDTO dto) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gênero não encontrado com ID: " + id));
        genero.setNome(dto.nome());
        genero = generoRepository.save(genero);
        return new GeneroResponseDTO(genero);
    }

    public void remover(Long id) {
        if (!generoRepository.existsById(id)) {
            throw new EntityNotFoundException("Gênero não encontrado com ID: " + id);
        }
        generoRepository.deleteById(id);
    }
}
