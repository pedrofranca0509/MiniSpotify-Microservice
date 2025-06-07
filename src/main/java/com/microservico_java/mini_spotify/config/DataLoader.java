package com.microservico_java.mini_spotify.config;

import com.microservico_java.mini_spotify.model.*;
import com.microservico_java.mini_spotify.repository.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(
            UsuarioRepository usuarioRepository,
            GeneroRepository generoRepository,
            MusicaRepository musicaRepository,
            PlaylistRepository playlistRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            // Verifica e cria usuários
            Usuario u1 = usuarioRepository.findByEmail("admin@email.com")
                    .orElseGet(() -> {
                        Usuario novo = new Usuario();
                        novo.setNome("admin");
                        novo.setEmail("admin@email.com");
                        novo.setSenha(passwordEncoder.encode("admin123"));
                        return usuarioRepository.save(novo);
                    });

            Usuario u2 = usuarioRepository.findByEmail("maria@email.com")
                    .orElseGet(() -> {
                        Usuario novo = new Usuario();
                        novo.setNome("Maria Souza");
                        novo.setEmail("maria@email.com");
                        novo.setSenha(passwordEncoder.encode("senha456"));
                        return usuarioRepository.save(novo);
                    });

            // Verifica e cria gêneros
            Genero g1 = generoRepository.findByNome("Rock")
                    .orElseGet(() -> {
                        Genero novo = new Genero();
                        novo.setNome("Rock");
                        return generoRepository.save(novo);
                    });

            Genero g2 = generoRepository.findByNome("Pop")
                    .orElseGet(() -> {
                        Genero novo = new Genero();
                        novo.setNome("Pop");
                        return generoRepository.save(novo);
                    });

            // Verifica e cria músicas
            Musica m1 = musicaRepository.findByTitulo("Bohemian Rhapsody")
                    .orElseGet(() -> {
                        Musica nova = new Musica();
                        nova.setTitulo("Bohemian Rhapsody");
                        nova.setArtista("Queen");
                        nova.setDuracaoSegundos(354);
                        nova.setGenero(g1);
                        return musicaRepository.save(nova);
                    });

            Musica m2 = musicaRepository.findByTitulo("Bad Guy")
                    .orElseGet(() -> {
                        Musica nova = new Musica();
                        nova.setTitulo("Bad Guy");
                        nova.setArtista("Billie Eilish");
                        nova.setDuracaoSegundos(194);
                        nova.setGenero(g2);
                        return musicaRepository.save(nova);
                    });

            // Verifica e cria playlists
            if (!playlistRepository.existsByNomeAndUsuario("Favoritas do João", u1)) {
                Playlist p1 = new Playlist();
                p1.setNome("Favoritas do João");
                p1.setUsuario(u1);
                p1.getMusicas().add(m1);
                playlistRepository.save(p1);
            }

            if (!playlistRepository.existsByNomeAndUsuario("Hits da Maria", u2)) {
                Playlist p2 = new Playlist();
                p2.setNome("Hits da Maria");
                p2.setUsuario(u2);
                p2.getMusicas().add(m2);
                playlistRepository.save(p2);
            }

            System.out.println("Dados carregados com sucesso!");
        };
    }
}
