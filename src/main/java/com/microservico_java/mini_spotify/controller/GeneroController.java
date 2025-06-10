package com.microservico_java.mini_spotify.controller;

import com.microservico_java.mini_spotify.dto.GeneroRequestDTO;
import com.microservico_java.mini_spotify.dto.GeneroResponseDTO;
import com.microservico_java.mini_spotify.service.GeneroService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @PostMapping
    public ResponseEntity<GeneroResponseDTO> criar(@RequestBody @Valid GeneroRequestDTO dto) {
        return ResponseEntity.ok(generoService.criar(dto));
    }

    @GetMapping
    public List<GeneroResponseDTO> listarTodos() {
        return generoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneroResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(generoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneroResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid GeneroRequestDTO dto) {
        return ResponseEntity.ok(generoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        generoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
