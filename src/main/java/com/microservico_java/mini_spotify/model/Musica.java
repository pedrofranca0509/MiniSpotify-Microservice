package com.microservico_java.mini_spotify.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Set;
import java.util.HashSet;

@Entity
@Data
public class Musica {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;
    private Integer duracaoSegundos;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "genero_id")
    private Genero genero;

    @ManyToMany(mappedBy = "musicas")
    private Set<Playlist> playlists = new HashSet<>();
}