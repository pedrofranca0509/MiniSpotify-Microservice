# MiniSpotify - Microserviço - Em desenvolvimento

<h1>--- Í N D I C E ---</h1>

* Sobre o projeto
* Funcionalidades
* Tecnologia Utilizadas
* Como Executar
* Estrutura de Pastas
* Contribuições
  

<h2>→ Sobre o projeto :</h2>
Este projeto é um clone do Spotify, com foco em replicar funcionalidades como reprodução de músicas, playlists, navegação entre páginas e integração com APIs. O projeto consiste na criação de um microserviço em Java com Spring Boot, focado no gerenciamento de músicas, gêneros, playlists e usuários. Ele implementa autenticação e autorização com JWT, possui rotas públicas para consulta de músicas e álbuns e rotas protegidas para operações como criação e edição de playlists e perfis. O sistema integra-se com um banco de dados PostgreSQL, utiliza Spring Security para segurança e oferece documentação da API via Swagger.

<h2>→ Funcionalidades :</h2>

  * Autenticação (login/cadastro).
  * Página inicial com destaques.
  * Listagem de artistas, playlists e usuario.
  * Reprodutor de música com play/pause, volume e progresso.

<h2>→ Tecnologia Utilizadas :</h2>

  -- BACK-END -- 
  * Java versão 24
  * Spring booot 3.4.6
  * Banco de dados H2 (desenvolvimento)
  * PostgreSQL (produção)
  * KIT JAVA (extenções)

  -- FRONT-END --
  * JS
  * HTML 
  * CSS


<h2>→ Como Executar :</h2>

<h2>→ Estrutura de Pastas :</h2>

MiniSpotify-Microservice

├── .mvn/wrapper/
│ └── maven-wrapper.properties

├── data/
│ └── minispotify.trace.db

├── src/
│ └── main/
| │ └── java/com/microservico_java/mini_spotify/
| | │ └── config/
| | │ │ └── DataLoader.java/
| | │ └── controller/
| | │ │ └── AuthController.java/
| | │ │ └── GeneroController.java/
| | │ │ └── MusicaController.java/
| | │ │ └── PlaylistController.java/
| | │ │ └── UsuarioController.java/
| | │ └── dto/
| | │ │ └── GeneroRequestDTO.java/  
| | │ │ └── eneroResponseDTO.java/
| | │ │ └── LoginRequestDTO.java/
| | │ │ └── MusicaRequestDTO.java/
| | │ │ └── MusicaResponseDTO.java/
| | │ │ └── PlaylistRequestDTO.java/
| | │ │ └── PlaylistResponseDTO.java/
| | │ │ └── UsuarioRequestDTO.java/
| | │ │ └── UsuarioResponseDTO.java/
| | │ └── model/
| | │ │ └── Genero.java/
| | │ │ └── Musica.java/
| | │ │ └── Playlist.java/
| | │ │ └── Usuario.java/
| | │ └── repository/
| | │ │ └── GeneroRepository.java
| | │ │ └── MusicaRepository.java
| | │ │ └── PlaylistRepository.java
| | │ │ └── UsuarioRepository.java
| | │ └── security/
| | │ │ └── jwt/
| | │ │ | └── JwtAuthenticationFilter.java/
| | │ │ | └── JwtUtil.java/
| | │ │ └── service/
| | │ │ | └── UsuarioDetailsService.java/
| | │ └── SecurityConfig.java/
| | │ └── SwaggerConfig.java/
| | │ └── service/
| | │ │ └── GeneroService.java/
| | │ │ └── MusicaService.java/
| | │ │ └── PlaylistService.java/
| | │ │ └── UsuarioService.java/
| | │ └── MiniSpotifyApplication.java/
| │ └── resources/
| | │ └── _data.sql
| | │ └── application.properties
| | │ └── application-dev.properties
| | │ └── application-prod.properties
│ └── test/java/com/microservico_java/mini_spotify
| │ └── controller/
| | │ └── GeneroControllerTest.java
| | │ └── MusicaControllerTest.java
| | │ └── UsuarioControllerTest.java
| │ └── service/
| | │ └── GeneroServiceTest.java
| | │ └── MusicaServiceTest.java
| | │ └── UsuarioServiceTest.java
| │ └── MiniSpotifyApplicationTests.java

├── static
│ └── index.html
│ └── script.js
│ └── style.css

├── .env.example
├── .gitattributes
├── .gitignore
├── README.md
├── mvnw
├── mvnw.cmd
└── pom.xml

<h2>→ Contribuições :</h2>



<h2>→ Tecnologia Utilizadas :</h2>
 -- BACK-END --
 * Java versão 17
 * Spring booot 3.4.6
 * Maven
 * Spring Security + JWT
 * Spring Data JPA
 * Docker
 * Swagger/OpenAPI
 * Banco de dados H2 (desenvolvimento)
 * PostgreSQL (produção)
 * KIT JAVA/SPRING BOOT (extensões)
 -- FRONT-END --
 * JS
 * HTML
 * CSS
    




