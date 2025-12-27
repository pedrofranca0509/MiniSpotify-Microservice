package com.microservico_java.mini_spotify.controller;

import com.microservico_java.mini_spotify.dto.MusicaRequestDTO;
import com.microservico_java.mini_spotify.dto.MusicaResponseDTO;
import com.microservico_java.mini_spotify.service.MusicaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MusicaControllerTest {

    @Mock
    private MusicaService musicaService;

    @InjectMocks
    private MusicaController musicaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarTodas_deveRetornarListaDeMusicas() {
        List<MusicaResponseDTO> lista = List.of(
                new MusicaResponseDTO(1L, "Música 1", "Artista 1", 180, "Rock"),
                new MusicaResponseDTO(2L, "Música 2", "Artista 2", 200, "Pop")
        );

        when(musicaService.listarTodas()).thenReturn(lista);

        ResponseEntity<List<MusicaResponseDTO>> resultado = musicaController.listarTodas();

        assertEquals(lista, resultado.getBody());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        verify(musicaService, times(1)).listarTodas();
    }

    @Test
    void buscarPorId_deveRetornarMusica() {
        MusicaResponseDTO response = new MusicaResponseDTO(1L, "Música 1", "Artista 1", 180, "Rock");

        when(musicaService.buscarPorId(1L)).thenReturn(response);

        ResponseEntity<MusicaResponseDTO> resultado = musicaController.buscarPorId(1L);

        assertEquals(response, resultado.getBody());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        verify(musicaService, times(1)).buscarPorId(1L);
    }

    @Test
    void buscarPorTitulo_deveRetornarListaFiltrada() {
        List<MusicaResponseDTO> lista = List.of(
                new MusicaResponseDTO(1L, "Música buscada", "Artista 1", 180, "Rock")
        );

        when(musicaService.buscarPorTitulo("buscada")).thenReturn(lista);

        ResponseEntity<List<MusicaResponseDTO>> resultado = musicaController.buscarPorTitulo("buscada");

        assertEquals(lista, resultado.getBody());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        verify(musicaService, times(1)).buscarPorTitulo("buscada");
    }

    @Test
    void salvar_deveRetornarMusicaCriada() {
        MusicaRequestDTO request = new MusicaRequestDTO("Nova Música", "Novo Artista", 210, 1L);
        MusicaResponseDTO response = new MusicaResponseDTO(1L, "Nova Música", "Novo Artista", 210, "Rock");

        when(musicaService.salvar(request)).thenReturn(response);

        ResponseEntity<MusicaResponseDTO> resultado = musicaController.salvar(request);

        assertEquals(response, resultado.getBody());
        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        verify(musicaService, times(1)).salvar(request);
    }

    @Test
    void atualizar_deveRetornarMusicaAtualizada() {
        MusicaRequestDTO request = new MusicaRequestDTO("Música Atualizada", "Artista Atualizado", 220, 2L);
        MusicaResponseDTO response = new MusicaResponseDTO(1L, "Música Atualizada", "Artista Atualizado", 220, "Pop");

        when(musicaService.atualizar(1L, request)).thenReturn(response);

        ResponseEntity<MusicaResponseDTO> resultado = musicaController.atualizar(1L, request);

        assertEquals(response, resultado.getBody());
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        verify(musicaService, times(1)).atualizar(1L, request);
    }

    @Test
    void deletar_deveChamarServiceEDarNoContent() {
        doNothing().when(musicaService).deletar(1L);

        ResponseEntity<Void> resultado = musicaController.deletar(1L);

        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
        verify(musicaService, times(1)).deletar(1L);
    }
}