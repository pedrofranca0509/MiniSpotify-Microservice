package com.microservico_java.mini_spotify.controller;

import com.microservico_java.mini_spotify.dto.GeneroRequestDTO;
import com.microservico_java.mini_spotify.dto.GeneroResponseDTO;
import com.microservico_java.mini_spotify.service.GeneroService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GeneroControllerTest {

    private GeneroService generoService;
    private GeneroController generoController;

    @BeforeEach
    void setUp() {
        generoService = mock(GeneroService.class);
        generoController = new GeneroController(generoService);
    }

    @Test
    void testCriar_Sucesso() {
        GeneroRequestDTO request = new GeneroRequestDTO("Rock");
        GeneroResponseDTO responseDTO = new GeneroResponseDTO(1L, "Rock");

        when(generoService.criar(any())).thenReturn(responseDTO);

        ResponseEntity<GeneroResponseDTO> response = generoController.criar(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Rock", response.getBody().nome());

        verify(generoService).criar(request);
    }

    @Test
    void testListarTodos_Sucesso() {
        List<GeneroResponseDTO> lista = Arrays.asList(
            new GeneroResponseDTO(1L, "Rock"),
            new GeneroResponseDTO(2L, "Pop")
        );

        when(generoService.listarTodos()).thenReturn(lista);

        List<GeneroResponseDTO> response = generoController.listarTodos();

        assertNotNull(response);
        assertEquals(2, response.size());
        verify(generoService).listarTodos();
    }

    @Test
    void testBuscarPorId_Sucesso() {
        GeneroResponseDTO responseDTO = new GeneroResponseDTO(1L, "Rock");

        when(generoService.buscarPorId(1L)).thenReturn(responseDTO);

        ResponseEntity<GeneroResponseDTO> response = generoController.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Rock", response.getBody().nome());

        verify(generoService).buscarPorId(1L);
    }

    @Test
    void testBuscarPorId_NaoEncontrado() {
        when(generoService.buscarPorId(999L)).thenThrow(new EntityNotFoundException("Gênero não encontrado com ID: 999"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            generoController.buscarPorId(999L);
        });

        assertEquals("Gênero não encontrado com ID: 999", exception.getMessage());
        verify(generoService).buscarPorId(999L);
    }

    @Test
    void testAtualizar_Sucesso() {
        GeneroRequestDTO request = new GeneroRequestDTO("Jazz");
        GeneroResponseDTO responseDTO = new GeneroResponseDTO(1L, "Jazz");

        when(generoService.atualizar(eq(1L), any())).thenReturn(responseDTO);

        ResponseEntity<GeneroResponseDTO> response = generoController.atualizar(1L, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Jazz", response.getBody().nome());

        verify(generoService).atualizar(1L, request);
    }

    @Test
    void testRemover_Sucesso() {
        doNothing().when(generoService).remover(1L);

        ResponseEntity<Void> response = generoController.remover(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(generoService).remover(1L);
    }
}
