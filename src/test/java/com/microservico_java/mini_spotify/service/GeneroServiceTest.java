package com.microservico_java.mini_spotify.service;

import com.microservico_java.mini_spotify.dto.GeneroRequestDTO;
import com.microservico_java.mini_spotify.dto.GeneroResponseDTO;
import com.microservico_java.mini_spotify.model.Genero;
import com.microservico_java.mini_spotify.repository.GeneroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GeneroServiceTest {

    @InjectMocks
    private GeneroService generoService;

    @Mock
    private GeneroRepository generoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarGenero_Sucesso() {
        GeneroRequestDTO dto = new GeneroRequestDTO("Rock");
        Genero generoSalvo = new Genero("Rock");
        generoSalvo.setId(1L);

        when(generoRepository.save(any(Genero.class))).thenReturn(generoSalvo);

        GeneroResponseDTO response = generoService.criar(dto);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Rock", response.nome());
        verify(generoRepository, times(1)).save(any(Genero.class));
    }

    @Test
    void listarTodos_Sucesso() {
        Genero genero1 = new Genero("Rock");
        genero1.setId(1L);
        Genero genero2 = new Genero("Pop");
        genero2.setId(2L);

        when(generoRepository.findAll()).thenReturn(List.of(genero1, genero2));

        List<GeneroResponseDTO> lista = generoService.listarTodos();

        assertEquals(2, lista.size());
        assertEquals("Rock", lista.get(0).nome());
        assertEquals("Pop", lista.get(1).nome());
        verify(generoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_Sucesso() {
        Genero genero = new Genero("Rock");
        genero.setId(1L);

        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));

        GeneroResponseDTO response = generoService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Rock", response.nome());
        verify(generoRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_NaoEncontrado() {
        when(generoRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            generoService.buscarPorId(99L);
        });

        assertEquals("Gênero não encontrado com ID: 99", exception.getMessage());
        verify(generoRepository, times(1)).findById(99L);
    }

    @Test
    void atualizar_Sucesso() {
        GeneroRequestDTO dto = new GeneroRequestDTO("Rock Atualizado");
        Genero generoExistente = new Genero("Rock");
        generoExistente.setId(1L);

        Genero generoAtualizado = new Genero("Rock Atualizado");
        generoAtualizado.setId(1L);

        when(generoRepository.findById(1L)).thenReturn(Optional.of(generoExistente));
        when(generoRepository.save(any(Genero.class))).thenReturn(generoAtualizado);

        GeneroResponseDTO response = generoService.atualizar(1L, dto);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Rock Atualizado", response.nome());
        verify(generoRepository, times(1)).findById(1L);
        verify(generoRepository, times(1)).save(any(Genero.class));
    }

    @Test
    void atualizar_NaoEncontrado() {
        GeneroRequestDTO dto = new GeneroRequestDTO("Rock Atualizado");

        when(generoRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            generoService.atualizar(99L, dto);
        });

        assertEquals("Gênero não encontrado com ID: 99", exception.getMessage());
        verify(generoRepository, times(1)).findById(99L);
    }

    @Test
    void remover_Sucesso() {
        when(generoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(generoRepository).deleteById(1L);

        assertDoesNotThrow(() -> generoService.remover(1L));
        verify(generoRepository, times(1)).existsById(1L);
        verify(generoRepository, times(1)).deleteById(1L);
    }

    @Test
    void remover_NaoEncontrado() {
        when(generoRepository.existsById(99L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            generoService.remover(99L);
        });

        assertEquals("Gênero não encontrado com ID: 99", exception.getMessage());
        verify(generoRepository, times(1)).existsById(99L);
        verify(generoRepository, never()).deleteById(anyLong());
    }
}
