package com.microservico_java.mini_spotify.service;

import com.microservico_java.mini_spotify.dto.PlaylistRequestDTO;
import com.microservico_java.mini_spotify.dto.PlaylistResponseDTO;
import com.microservico_java.mini_spotify.model.Musica;
import com.microservico_java.mini_spotify.model.Playlist;
import com.microservico_java.mini_spotify.model.Usuario;
import com.microservico_java.mini_spotify.repository.MusicaRepository;
import com.microservico_java.mini_spotify.repository.PlaylistRepository;
import com.microservico_java.mini_spotify.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private MusicaRepository musicaRepository;

    @InjectMocks
    private PlaylistService playlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarPlaylist_deveRetornarPlaylistResponseDTO_QuandoSucesso() {
        // Arrange
        PlaylistRequestDTO request = new PlaylistRequestDTO("Minha Playlist", 1L, List.of(1L, 2L));
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Musica musica1 = new Musica();
        musica1.setId(1L);
        Musica musica2 = new Musica();
        musica2.setId(2L);
        Playlist playlistSalva = new Playlist();
        playlistSalva.setId(1L);
        playlistSalva.setNome("Minha Playlist");
        playlistSalva.setUsuario(usuario);
        playlistSalva.setMusicas(Set.of(musica1, musica2));

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(musicaRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(musica1, musica2));
        when(playlistRepository.save(any(Playlist.class))).thenReturn(playlistSalva);

        // Act
        PlaylistResponseDTO response = playlistService.criarPlaylist(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Minha Playlist", response.nome());
        assertEquals(2, response.musicas().size());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(musicaRepository, times(1)).findAllById(List.of(1L, 2L));
        verify(playlistRepository, times(1)).save(any(Playlist.class));
    }

    @Test
    void criarPlaylist_deveLancarExcecao_QuandoUsuarioNaoExiste() {
        // Arrange
        PlaylistRequestDTO request = new PlaylistRequestDTO("Minha Playlist", 99L, null);

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> playlistService.criarPlaylist(request));
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Usuário com ID 99 não encontrado"));
    }

    @Test
    void criarPlaylist_deveLancarExcecao_QuandoMusicaNaoExiste() {
        // Arrange
        PlaylistRequestDTO request = new PlaylistRequestDTO("Minha Playlist", 1L, List.of(1L, 99L));
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Musica musica1 = new Musica();
        musica1.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(musicaRepository.findAllById(List.of(1L, 99L))).thenReturn(List.of(musica1));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> playlistService.criarPlaylist(request));
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Uma ou mais músicas não foram encontradas"));
    }

    @Test
    void listarTodas_deveRetornarListaDePlaylists() {
        // Arrange
        Playlist playlist1 = new Playlist();
        playlist1.setId(1L);
        playlist1.setNome("Playlist 1");
        
        Playlist playlist2 = new Playlist();
        playlist2.setId(2L);
        playlist2.setNome("Playlist 2");

        when(playlistRepository.findAllWithMusicasAndUsuario()).thenReturn(List.of(playlist1, playlist2));

        // Act
        List<PlaylistResponseDTO> result = playlistService.listarTodas();

        // Assert
        assertEquals(2, result.size());
        verify(playlistRepository, times(1)).findAllWithMusicasAndUsuario();
    }

    @Test
    void buscarPorId_deveRetornarPlaylist_QuandoExistir() {
        // Arrange
        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setNome("Minha Playlist");

        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));

        // Act
        PlaylistResponseDTO result = playlistService.buscarPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Minha Playlist", result.nome());
        verify(playlistRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_deveLancarExcecao_QuandoNaoExistir() {
        // Arrange
        when(playlistRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> playlistService.buscarPorId(1L));
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Playlist não encontrada"));
    }

    @Test
    void atualizar_deveAtualizarPlaylist_QuandoExistir() {
        // Arrange
        Playlist playlistExistente = new Playlist();
        playlistExistente.setId(1L);
        playlistExistente.setNome("Playlist Antiga");

        Usuario novoUsuario = new Usuario();
        novoUsuario.setId(2L);
        
        Musica musica1 = new Musica();
        musica1.setId(1L);
        Musica musica2 = new Musica();
        musica2.setId(2L);

        Playlist playlistAtualizada = new Playlist();
        playlistAtualizada.setId(1L);
        playlistAtualizada.setNome("Playlist Atualizada");
        playlistAtualizada.setUsuario(novoUsuario);
        playlistAtualizada.setMusicas(Set.of(musica1, musica2));

        PlaylistRequestDTO request = new PlaylistRequestDTO(
                "Playlist Atualizada",
                2L,
                List.of(1L, 2L));

        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlistExistente));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(novoUsuario));
        when(musicaRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(musica1, musica2));
        when(playlistRepository.save(any(Playlist.class))).thenReturn(playlistAtualizada);

        // Act
        PlaylistResponseDTO result = playlistService.atualizar(1L, request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Playlist Atualizada", result.nome());
        verify(playlistRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).findById(2L);
        verify(musicaRepository, times(1)).findAllById(List.of(1L, 2L));
        verify(playlistRepository, times(1)).save(any(Playlist.class));
    }

    @Test
    void deletar_deveDeletarPlaylist_QuandoExistir() {
        // Arrange
        when(playlistRepository.existsById(1L)).thenReturn(true);

        // Act
        playlistService.deletar(1L);

        // Assert
        verify(playlistRepository, times(1)).existsById(1L);
        verify(playlistRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletar_deveLancarExcecao_QuandoNaoExistir() {
        // Arrange
        when(playlistRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> playlistService.deletar(1L));
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Playlist não encontrada"));
    }
}