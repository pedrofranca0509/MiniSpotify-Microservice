package com.microservico_java.mini_spotify.service;

import com.microservico_java.mini_spotify.dto.MusicaRequestDTO;
import com.microservico_java.mini_spotify.dto.MusicaResponseDTO;
import com.microservico_java.mini_spotify.model.Genero;
import com.microservico_java.mini_spotify.model.Musica;
import com.microservico_java.mini_spotify.repository.GeneroRepository;
import com.microservico_java.mini_spotify.repository.MusicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MusicaServiceTest {

    @Mock
    private MusicaRepository musicaRepository;

    @Mock
    private GeneroRepository generoRepository;

    @InjectMocks
    private MusicaService musicaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvar_DeveSalvarMusicaComGeneroExistente() {
        MusicaRequestDTO dto = new MusicaRequestDTO("Título", "Artista", 200, 1L);
        Genero genero = new Genero();
        genero.setId(1L);
        genero.setNome("Rock");

        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        when(musicaRepository.save(any(Musica.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MusicaResponseDTO response = musicaService.salvar(dto);

        assertEquals("Título", response.titulo());
        assertEquals("Artista", response.artista());
        assertEquals(200, response.duracaoSegundos());
        assertEquals("Rock", response.nomeGenero());
    }

    @Test
    void salvar_DeveLancarExcecao_QuandoGeneroNaoExistir() {
        MusicaRequestDTO dto = new MusicaRequestDTO("Título", "Artista", 200, 99L);
        when(generoRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> musicaService.salvar(dto));
        assertEquals("400 BAD_REQUEST \"Gênero não encontrado\"", ex.getMessage());
    }

    @Test
    void buscarPorId_DeveRetornarMusicaResponseDTO_QuandoExistente() {
        Musica musica = new Musica("Título", "Artista", 150, null);
        musica.setId(1L);

        when(musicaRepository.findById(1L)).thenReturn(Optional.of(musica));

        MusicaResponseDTO response = musicaService.buscarPorId(1L);
        assertEquals("Título", response.titulo());
    }

    @Test
    void buscarPorId_DeveLancarExcecao_QuandoNaoExistente() {
        when(musicaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> musicaService.buscarPorId(1L));
    }

    @Test
    void deletar_DeveExcluir_QuandoIdExistente() {
        when(musicaRepository.existsById(1L)).thenReturn(true);

        musicaService.deletar(1L);

        verify(musicaRepository).deleteById(1L);
    }

    @Test
    void deletar_DeveLancarExcecao_QuandoIdInexistente() {
        when(musicaRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> musicaService.deletar(1L));
    }

    @Test
    void listarTodas_DeveRetornarListaDeDTOs() {
        Musica musica = new Musica("M1", "A1", 180, null);
        musica.setId(1L);
        when(musicaRepository.findAll()).thenReturn(List.of(musica));

        List<MusicaResponseDTO> lista = musicaService.listarTodas();
        assertEquals(1, lista.size());
        assertEquals("M1", lista.get(0).titulo());
    }

    @Test
    void atualizar_DeveAlterarMusicaExistente() {
        Musica musica = new Musica("Antigo", "Artista", 100, null);
        musica.setId(1L);

        Genero genero = new Genero();
        genero.setId(1L);
        genero.setNome("Pop");

        MusicaRequestDTO dto = new MusicaRequestDTO("Novo", "Artista", 100, 1L);

        when(musicaRepository.findById(1L)).thenReturn(Optional.of(musica));
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        when(musicaRepository.save(any(Musica.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MusicaResponseDTO response = musicaService.atualizar(1L, dto);
        assertEquals("Novo", response.titulo());
        assertEquals("Pop", response.nomeGenero());
    }

    @Test
    void buscarPorTitulo_DeveRetornarListaFiltrada() {
        Musica musica = new Musica("Título Exato", "A", 200, null);
        when(musicaRepository.findByTituloContainingIgnoreCase("título")).thenReturn(List.of(musica));

        List<MusicaResponseDTO> resultado = musicaService.buscarPorTitulo("título");
        assertEquals(1, resultado.size());
        assertEquals("Título Exato", resultado.get(0).titulo());
    }
}
