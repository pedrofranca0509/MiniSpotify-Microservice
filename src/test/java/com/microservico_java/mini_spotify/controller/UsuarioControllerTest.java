package com.microservico_java.mini_spotify.controller;

import com.microservico_java.mini_spotify.dto.UsuarioRequestDTO;
import com.microservico_java.mini_spotify.dto.UsuarioResponseDTO;
import com.microservico_java.mini_spotify.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criar_deveRetornarUsuarioResponseDTO() {
        UsuarioRequestDTO request = new UsuarioRequestDTO("Murilo", "murilo@example.com", "senha123");
        UsuarioResponseDTO esperado = new UsuarioResponseDTO(1L, "Murilo", "murilo@example.com");

        when(usuarioService.criarUsuario(request)).thenReturn(esperado);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.criar(request);
        UsuarioResponseDTO resultado = response.getBody();

        assertEquals(esperado, resultado);
        verify(usuarioService, times(1)).criarUsuario(request);
    }

    @Test
    void listarTodos_deveRetornarListaDeUsuarios() {
        List<UsuarioResponseDTO> lista = List.of(
                new UsuarioResponseDTO(1L, "Murilo", "murilo@example.com"),
                new UsuarioResponseDTO(2L, "Ana", "ana@example.com")
        );

        when(usuarioService.listarTodos()).thenReturn(lista);

        List<UsuarioResponseDTO> resultado = usuarioController.listarTodos();

        assertEquals(lista, resultado);
        verify(usuarioService, times(1)).listarTodos();
    }

    @Test
    void buscarPorId_deveRetornarUsuario() {
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Murilo", "murilo@example.com");

        when(usuarioService.buscarPorId(1L)).thenReturn(response);

        UsuarioResponseDTO resultado = usuarioController.buscarPorId(1L);

        assertEquals(response, resultado);
        verify(usuarioService, times(1)).buscarPorId(1L);
    }

    @Test
    void atualizar_deveRetornarUsuarioResponseDTO() {
        UsuarioRequestDTO request = new UsuarioRequestDTO("Murilo Atualizado", "murilo.novo@example.com", "senha123");
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Murilo Atualizado", "murilo.novo@example.com");

        when(usuarioService.atualizar(1L, request)).thenReturn(response);

        ResponseEntity<UsuarioResponseDTO> resultado = usuarioController.atualizar(1L, request);

        assertEquals(ResponseEntity.ok(response), resultado);
        verify(usuarioService, times(1)).atualizar(1L, request);
    }

    @Test
    void deletar_deveChamarServiceEDarNoContent() {
        doNothing().when(usuarioService).deletar(1L);

        ResponseEntity<Void> resultado = usuarioController.deletar(1L);

        assertEquals(ResponseEntity.noContent().build(), resultado);
        verify(usuarioService, times(1)).deletar(1L);
    }
}
