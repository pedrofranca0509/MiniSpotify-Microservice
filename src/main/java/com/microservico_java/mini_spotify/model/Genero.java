package com.microservico_java.mini_spotify.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;


@Entity
@Data
@NoArgsConstructor
public class Genero {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "genero")
    private List<Musica> musicas = new ArrayList<>();

    public Genero(String nome) {
    this.nome = nome;
    }
}