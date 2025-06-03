package com.microservico_java.mini_spotify.controller;

import com.microservico_java.mini_spotify.dto.PlaylistRequestDTO;
import com.microservico_java.mini_spotify.dto.PlaylistResponseDTO;
import com.microservico_java.mini_spotify.service.PlaylistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<PlaylistResponseDTO> criar(@RequestBody @Valid PlaylistRequestDTO request) {
        return ResponseEntity.status(201).body(playlistService.criarPlaylist(request));
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponseDTO>> listarTodas() {
        return ResponseEntity.ok(playlistService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponseDTO> atualizar(
        @PathVariable Long id,
        @RequestBody @Valid PlaylistRequestDTO request
    ) {
        return ResponseEntity.ok(playlistService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        playlistService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
