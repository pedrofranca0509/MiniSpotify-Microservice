package com.microservico_java.mini_spotify.model;

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

    @NotBlank @Email
    private String email;

    @NotBlank
    private String senha; // (Criptografar com BCrypt!)

    @OneToMany(mappedBy = "usuario")
    private List<Playlist> playlists = new ArrayList<>();
}