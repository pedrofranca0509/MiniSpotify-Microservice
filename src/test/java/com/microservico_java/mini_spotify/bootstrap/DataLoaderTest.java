package com.microservico_java.mini_spotify.bootstrap;

import com.microservico_java.mini_spotify.model.*;
import com.microservico_java.mini_spotify.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

class DataLoaderTest {

    private UsuarioRepository usuarioRepository;
    private GeneroRepository generoRepository;
    private MusicaRepository musicaRepository;
    private PlaylistRepository playlistRepository;
    private PasswordEncoder passwordEncoder;
    private DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        generoRepository = mock(GeneroRepository.class);
        musicaRepository = mock(MusicaRepository.class);
        playlistRepository = mock(PlaylistRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);

        dataLoader = new DataLoader();
    }

    @Test
    void loadData_ShouldPopulateDatabase_WhenEntitiesDoNotExist() throws Exception {
        when(usuarioRepository.findByEmail("joao@email.com")).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail("maria@email.com")).thenReturn(Optional.empty());
        when(generoRepository.findByNome("Rock")).thenReturn(Optional.empty());
        when(generoRepository.findByNome("Pop")).thenReturn(Optional.empty());
        when(musicaRepository.findByTitulo("Bohemian Rhapsody")).thenReturn(Optional.empty());
        when(musicaRepository.findByTitulo("Bad Guy")).thenReturn(Optional.empty());
        when(playlistRepository.existsByNomeAndUsuario(eq("Best of Rock"), any())).thenReturn(false);
        when(playlistRepository.existsByNomeAndUsuario(eq("Pop Songs"), any())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        CommandLineRunner runner = dataLoader.loadData(
                usuarioRepository,
                generoRepository,
                musicaRepository,
                playlistRepository,
                passwordEncoder
        );

        runner.run(); // Executa o método que popula os dados

        verify(usuarioRepository, times(2)).save(any(Usuario.class));
        verify(generoRepository, times(2)).save(any(Genero.class));
        verify(musicaRepository, times(2)).save(any(Musica.class));
        verify(playlistRepository, times(2)).save(any(Playlist.class));
    }
}
