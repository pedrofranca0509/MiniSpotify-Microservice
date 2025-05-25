package com.microservico_java.mini_spotify.repository;

import com.microservico_java.mini_spotify.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}