package com.microservico_java.mini_spotify.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String senha; // Tem que criptografar com BCrypt

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Playlist> playlists = new ArrayList<>();
}
