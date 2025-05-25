package com.microservico_java.mini_spotify.controller;

import com.microservico_java.mini_spotify.dto.PlaylistRequestDTO;
import com.microservico_java.mini_spotify.model.Playlist;
import com.microservico_java.mini_spotify.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    @PostMapping
    public Playlist criar(@RequestBody PlaylistRequestDTO request) {
        return playlistService.criarPlaylist(request);
    }

    @GetMapping
    public List<Playlist> listarTodas() {
        return playlistService.listarTodas();
    }
}