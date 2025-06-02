package com.microservico_java.mini_spotify.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
public class Album {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "artista")
    private String artista;

    private String nome;
    private Integer ano;

    @OneToMany(mappedBy = "album")
    private List<Musica> musicas = new ArrayList<>();
}