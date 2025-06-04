package com.microservico_java.mini_spotify.controller;

import com.microservico_java.mini_spotify.dto.MusicaResponseDTO;
import com.microservico_java.mini_spotify.dto.MusicaRequestDTO;
import com.microservico_java.mini_spotify.service.MusicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musicas")
@RequiredArgsConstructor
public class MusicaController {

    private final MusicaService musicaService;

    @GetMapping
    public ResponseEntity<List<MusicaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(musicaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(musicaService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<MusicaResponseDTO>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(musicaService.buscarPorTitulo(titulo));
    }

    @PostMapping
    public ResponseEntity<MusicaResponseDTO> salvar(@RequestBody @Valid MusicaRequestDTO dto) {
        return ResponseEntity.status(201).body(musicaService.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MusicaRequestDTO dto) {
        return ResponseEntity.ok(musicaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        musicaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
