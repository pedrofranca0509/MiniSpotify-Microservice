package com.microservico_java.mini_spotify.service;

import com.microservico_java.mini_spotify.dto.MusicaDTO;
import com.microservico_java.mini_spotify.model.Musica;
import com.microservico_java.mini_spotify.repository.MusicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicaService {
    private final MusicaRepository musicaRepository;

    public List<MusicaDTO> listarTodas() {
        return musicaRepository.findAll().stream()
                .map(MusicaDTO::new)
                .toList();
    }
}