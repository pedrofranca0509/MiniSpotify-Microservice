package com.microservico_java.mini_spotify.controller;

import com.microservico_java.mini_spotify.dto.PlaylistRequestDTO;
import com.microservico_java.mini_spotify.dto.PlaylistResponseDTO;
import com.microservico_java.mini_spotify.service.PlaylistService;
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

class PlaylistControllerTest {

    @Mock
    private PlaylistService playlistService;

    @InjectMocks
    private PlaylistController playlistController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criar_deveRetornarPlaylistCriadaComStatus201() {
        // Arrange
        PlaylistRequestDTO request = new PlaylistRequestDTO("Nova Playlist", 1L, List.of(1L, 2L));
        PlaylistResponseDTO response = new PlaylistResponseDTO(1L, "Nova Playlist", "Usuário 1", List.of());

        when(playlistService.criarPlaylist(request)).thenReturn(response);

        // Act
        ResponseEntity<PlaylistResponseDTO> result = playlistController.criar(request);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(playlistService, times(1)).criarPlaylist(request);
    }

    @Test
    void listarTodas_deveRetornarListaComStatus200() {
        // Arrange
        PlaylistResponseDTO playlist1 = new PlaylistResponseDTO(1L, "Playlist 1", "Usuário 1", List.of());
        PlaylistResponseDTO playlist2 = new PlaylistResponseDTO(2L, "Playlist 2", "Usuário 2", List.of());
        List<PlaylistResponseDTO> playlists = List.of(playlist1, playlist2);

        when(playlistService.listarTodas()).thenReturn(playlists);

        // Act
        ResponseEntity<List<PlaylistResponseDTO>> result = playlistController.listarTodas();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        verify(playlistService, times(1)).listarTodas();
    }

    @Test
    void buscarPorId_deveRetornarPlaylistComStatus200() {
        // Arrange
        PlaylistResponseDTO response = new PlaylistResponseDTO(1L, "Minha Playlist", "Usuário 1", List.of());

        when(playlistService.buscarPorId(1L)).thenReturn(response);

        // Act
        ResponseEntity<PlaylistResponseDTO> result = playlistController.buscarPorId(1L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(playlistService, times(1)).buscarPorId(1L);
    }

    @Test
    void atualizar_deveRetornarPlaylistAtualizadaComStatus200() {
        // Arrange
        PlaylistRequestDTO request = new PlaylistRequestDTO("Playlist Atualizada", 1L, List.of(1L, 2L));
        PlaylistResponseDTO response = new PlaylistResponseDTO(1L, "Playlist Atualizada", "Usuário 1", List.of());

        when(playlistService.atualizar(1L, request)).thenReturn(response);

        // Act
        ResponseEntity<PlaylistResponseDTO> result = playlistController.atualizar(1L, request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(playlistService, times(1)).atualizar(1L, request);
    }

    @Test
    void deletar_deveRetornarStatus204() {
        // Arrange
        doNothing().when(playlistService).deletar(1L);

        // Act
        ResponseEntity<Void> result = playlistController.deletar(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(playlistService, times(1)).deletar(1L);
    }

    @Test
    void buscarPorId_deveLancarExcecaoQuandoNaoEncontrado() {
    // Arrange
        Long idInvalido = 999L;
        when(playlistService.buscarPorId(idInvalido)).thenThrow(new RuntimeException("Playlist não encontrada"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            playlistController.buscarPorId(idInvalido);
        });

        assertEquals("Playlist não encontrada", exception.getMessage());
        verify(playlistService, times(1)).buscarPorId(idInvalido);
    }

    @Test
    void atualizar_deveLancarExcecaoQuandoIdNaoExiste() {
        // Arrange
        Long idInvalido = 999L;
        PlaylistRequestDTO request = new PlaylistRequestDTO("Inexistente", 1L, List.of(1L));

        when(playlistService.atualizar(idInvalido, request))
            .thenThrow(new RuntimeException("Playlist não encontrada"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            playlistController.atualizar(idInvalido, request);
        });

        assertEquals("Playlist não encontrada", exception.getMessage());
        verify(playlistService, times(1)).atualizar(idInvalido, request);
    }

    @Test
    void criar_deveLancarExcecaoQuandoDadosInvalidos() {
        // Arrange
        PlaylistRequestDTO requestInvalido = new PlaylistRequestDTO("", null, null);

        when(playlistService.criarPlaylist(requestInvalido))
            .thenThrow(new IllegalArgumentException("Dados inválidos"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            playlistController.criar(requestInvalido);
        });

        assertEquals("Dados inválidos", exception.getMessage());
        verify(playlistService, times(1)).criarPlaylist(requestInvalido);
    }

    @Test
    void listarTodas_deveRetornarListaVaziaComStatus200() {
        // Arrange
        when(playlistService.listarTodas()).thenReturn(List.of());

        // Act
        ResponseEntity<List<PlaylistResponseDTO>> result = playlistController.listarTodas();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
        verify(playlistService, times(1)).listarTodas();
    }

}