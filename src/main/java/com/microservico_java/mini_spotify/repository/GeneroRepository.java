package com.microservico_java.mini_spotify.repository;


import com.microservico_java.mini_spotify.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

    // Busca por nome exato
    Optional<Genero> findByNome(String nome);

    // Busca por nome contendo (case insensitive)
    List<Genero> findByNomeContainingIgnoreCase(String nome);
}