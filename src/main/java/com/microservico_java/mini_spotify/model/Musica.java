package com.microservico_java.mini_spotify.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.Objects;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
public class Musica {
     @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "titulo")
    private String titulo;
    private Integer duracaoSegundos;

    @Column(name = "artista")
    private String artista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genero_id")
    private Genero genero;

    @ManyToMany(mappedBy = "musicas")
    @JsonIgnore
    private Set<Playlist> playlists = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Musica musica = (Musica) o;
        return Objects.equals(id, musica.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Musica(String titulo, String artista, Integer duracaoSegundos, Genero genero) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracaoSegundos = duracaoSegundos;
        this.genero = genero;
    }
}