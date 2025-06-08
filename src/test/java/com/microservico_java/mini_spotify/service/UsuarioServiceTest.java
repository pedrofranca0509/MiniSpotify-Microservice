package com.microservico_java.mini_spotify.service;

import com.microservico_java.mini_spotify.dto.UsuarioRequestDTO;
import com.microservico_java.mini_spotify.dto.UsuarioResponseDTO;
import com.microservico_java.mini_spotify.model.Usuario;
import com.microservico_java.mini_spotify.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarUsuario_comEmailExistente_deveLancarException() {
        UsuarioRequestDTO request = new UsuarioRequestDTO("Murilo", "murilo@example.com", "senha123");

        when(usuarioRepository.existsByEmail(request.email())).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            usuarioService.criarUsuario(request);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Email já está em uso", exception.getReason());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void criarUsuario_comEmailNovo_deveSalvarESerRetornado() {
        UsuarioRequestDTO request = new UsuarioRequestDTO("Murilo", "murilo@example.com", "senha123");

        when(usuarioRepository.existsByEmail(request.email())).thenReturn(false);
        when(passwordEncoder.encode(request.senha())).thenReturn("senhaCriptografada");

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setId(1L);
        usuarioSalvo.setNome(request.nome());
        usuarioSalvo.setEmail(request.email());
        usuarioSalvo.setSenha("senhaCriptografada");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        UsuarioResponseDTO response = usuarioService.criarUsuario(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Murilo", response.nome());
        assertEquals("murilo@example.com", response.email());

        verify(usuarioRepository).existsByEmail(request.email());
        verify(passwordEncoder).encode(request.senha());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void listarTodos_deveRetornarListaUsuarios() {
        Usuario u1 = new Usuario();
        u1.setId(1L);
        u1.setNome("Murilo");
        u1.setEmail("murilo@example.com");

        Usuario u2 = new Usuario();
        u2.setId(2L);
        u2.setNome("Ana");
        u2.setEmail("ana@example.com");

        when(usuarioRepository.findAll()).thenReturn(List.of(u1, u2));

        List<UsuarioResponseDTO> lista = usuarioService.listarTodos();

        assertEquals(2, lista.size());
        assertEquals("Murilo", lista.get(0).nome());
        assertEquals("Ana", lista.get(1).nome());

        verify(usuarioRepository).findAll();
    }

    @Test
    void buscarPorId_usuarioExistente_deveRetornar() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Murilo");
        usuario.setEmail("murilo@example.com");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO response = usuarioService.buscarPorId(1L);

        assertEquals(1L, response.id());
        assertEquals("Murilo", response.nome());

        verify(usuarioRepository).findById(1L);
    }

    @Test
    void buscarPorId_usuarioNaoExistente_deveLancarException() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            usuarioService.buscarPorId(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Usuário não encontrado", exception.getReason());
    }

    @Test
    void atualizar_usuarioExistente_eSenhaDiferente_deveAtualizarSenha() {
        UsuarioRequestDTO request = new UsuarioRequestDTO("Murilo Atualizado", "murilo.novo@example.com", "novaSenha");
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setNome("Murilo");
        usuarioExistente.setEmail("murilo@example.com");
        usuarioExistente.setSenha("senhaAntigaCriptografada");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(passwordEncoder.matches(request.senha(), usuarioExistente.getSenha())).thenReturn(false);
        when(passwordEncoder.encode(request.senha())).thenReturn("senhaNovaCriptografada");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        UsuarioResponseDTO response = usuarioService.atualizar(1L, request);

        assertEquals("Murilo Atualizado", response.nome());
        assertEquals("murilo.novo@example.com", response.email());
        assertEquals(1L, response.id());

        verify(passwordEncoder).matches(request.senha(), "senhaAntigaCriptografada");
        verify(passwordEncoder).encode(request.senha());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void atualizar_usuarioExistente_eSenhaIgual_naoAtualizaSenha() {
        UsuarioRequestDTO request = new UsuarioRequestDTO("Murilo Atualizado", "murilo.novo@example.com", "senhaAtual");
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setNome("Murilo");
        usuarioExistente.setEmail("murilo@example.com");
        usuarioExistente.setSenha("senhaAtual");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(passwordEncoder.matches(request.senha(), usuarioExistente.getSenha())).thenReturn(true);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        UsuarioResponseDTO response = usuarioService.atualizar(1L, request);

        assertEquals("Murilo Atualizado", response.nome());
        assertEquals("murilo.novo@example.com", response.email());
        assertEquals(1L, response.id());

        verify(passwordEncoder).matches(request.senha(), "senhaAtual");
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void atualizar_usuarioNaoExistente_deveLancarException() {
        UsuarioRequestDTO request = new UsuarioRequestDTO("Murilo", "murilo@example.com", "senha123");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            usuarioService.atualizar(1L, request);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Usuário não encontrado", exception.getReason());
    }

    @Test
    void deletar_usuarioExistente_deveChamarDelete() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.deletar(1L);

        verify(usuarioRepository).existsById(1L);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void deletar_usuarioNaoExistente_deveLancarException() {
        when(usuarioRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            usuarioService.deletar(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Usuário não encontrado", exception.getReason());
        verify(usuarioRepository, never()).deleteById(anyLong());
    }
}
