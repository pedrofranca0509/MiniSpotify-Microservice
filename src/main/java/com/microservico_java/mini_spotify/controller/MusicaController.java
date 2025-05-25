package com.microservico_java.mini_spotify.controller;

import com.microservico_java.mini_spotify.dto.MusicaDTO;
import com.microservico_java.mini_spotify.service.MusicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/musicas")
@RequiredArgsConstructor
public class MusicaController {
    private final MusicaService musicaService;

    @GetMapping
    public List<MusicaDTO> listarTodas() {
        return musicaService.listarTodas();
    }
}