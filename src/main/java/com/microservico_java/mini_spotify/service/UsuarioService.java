package com.microservico_java.mini_spotify.service;

import com.microservico_java.mini_spotify.dto.UsuarioRequestDTO;
import com.microservico_java.mini_spotify.dto.UsuarioResponseDTO;
import com.microservico_java.mini_spotify.model.Usuario;
import com.microservico_java.mini_spotify.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já está em uso");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(request.senha()); // Recomendado criptografar no futuro

        Usuario salvo = usuarioRepository.save(usuario);
        return new UsuarioResponseDTO(salvo);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponseDTO::new)
                .toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return new UsuarioResponseDTO(usuario);
    }
}
