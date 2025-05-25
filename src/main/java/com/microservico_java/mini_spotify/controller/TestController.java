package com.microservico_java.mini_spotify.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Este é um endpoint público";
    }

    @GetMapping("/private")
    public String privateEndpoint() {
        return "Este é um endpoint privado";
    }
}