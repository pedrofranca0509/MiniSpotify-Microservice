# MiniSpotify - Microserviço

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

  * Autenticação com token.
  * Listagem, criação, exclusão e atualização de artistas, playlists, gêneros  e usuário.
  * Listagem específica utilizando ID.
  * Em músicas é possível fazer uma busca por título.

<h2>→ Tecnologia Utilizadas :</h2>

  -- BACK-END -- 
  * Java versão 17
  * Spring boot 3.4.6
  * Maven
  * Spring Security + JWT
  * Spring Data JPA
  * Docker
  * Swagger/OpenAPI
  * Banco de dados H2 (desenvolvimento)
  * PostgreSQL (produção)
  * Testes com JUnit 5 e Mockito, e JaCoCo para medir a cobertura - Relatóro JaCoCo disponível em: https://muril0pacheco.github.io/MiniSpotify-Microservice/jacoco-report/index.html   <br>   <br>
 
  -- FRONT-END --
  * JS
  * HTML 
  * CSS


<h2>→ Contribuições :</h2>
<h4>MURILO PACHECO - RA: 213390 ↓</h4>
Responsável pelo Back End<br>

<h4>NICOLAS MARQUES - RA: 078390 ↓</h4>
Auxilio no Back End e Elaboração do Front End<br>
  
<h4>ARTHUR ZAFFALON - RA: 078212 ↓</h4>
Elaboração do Figma, Documentação e Slides<br>

<h4>BIANCA FREIRE - RA: 078218 ↓</h4>
Elaboração do Figma, Documentação e Slides<br>

<h4>PEDRO FRANCA - RA: 078418 ↓</h4>
Elaboração do Back End<br>

<h4>RICARDO PETRAGLIA - RA: 207557 ↓</h4>
Elaboração do Figma, Documentação e Slides<br>

<h2>→ Como Executar :</h2>

<h3>→Rodar local:</h3><br>

*OBS : Tenha o jdk do java versão 17+(nosso pom.xml está na 17), PostgreSQl (versão mais
recente) e Maven (versão mais recente) instalados.*<br>

*1 - Clone o repositório:*

git clone
https://github.com/Muril0Pacheco/MiniSpotify-Microservice.git<br>

*2 - Crie uma database no PostgreSQL chamada mini_spotify<br>*
recente) e Maven (versão mais recente) instalados.* <br>

*3 - Crie um arquivo `.env` na raiz do projeto com o seguinte conteúdo(na raiz do projeto
temos o .env.example):*<br><br>
DB_URL=jdbc:postgresql://localhost:5432/mini_spotify<br>
DB_USERNAME= SEU_USUARIO_POSTGRES(Usuário padrão é 'postgres')<br>
DB_PASSWORD= SUA_SENHA_POSTGRES<br>
DDL_AUTO= update<br>
SERVER_PORT= 8080(Certifique-se que a porta não esteja sendo utilizada)<br>
JWT_SECRET= SUA_CHAVE_JWT(crie uma sequência de caracteres)<br>
<br>

*4 - No terminal da raiz do projeto execute:*<br>
Get-Content .env | ForEach-Object {<br>
 if ($_ -match "^\s*([^#][^=]*)=(.*)$") {<br>
 [System.Environment]::SetEnvironmentVariable($matches[1].Trim(),
$matches[2].Trim(), "Process")<br>
 }<br>
}<br>
./mvnw spring-boot:run<br>


<br>

*5 - Agora você pode testar no swagger:*
http://localhost:8080/swagger-ui/index.html#/
<br><br>


<h3>→Rodar online:</h2><br>
1 - Acesse o link a seguir e você entrará no swagger da aplicação hospedada no
render:<br>https://minispotify-microservice.onrender.com/swagger-ui/index.html#/*
<br><br>


<h2>→ Estrutura de Pastas :</h2>
MiniSpotify-Microservice
├── src/<br>
│ └── main/<br>
| │ └── java/com/microservico_java/mini_spotify/<br>
| | │ └── config/<br>
| | │ │ └── DataLoader.java/<br>
| | │ └── controller/<br>
| | │ │ └── AuthController.java/<br>
| | │ │ └── GeneroController.java/<br>
| | │ │ └── MusicaController.java/<br>
| | │ │ └── PlaylistController.java/<br>
| | │ │ └── UsuarioController.java/<br>
| | │ └── dto/<br>
| | │ │ └── GeneroRequestDTO.java/<br>
| | │ │ └── eneroResponseDTO.java/<br>
| | │ │ └── LoginRequestDTO.java/<br>
| | │ │ └── MusicaRequestDTO.java/<br>
| | │ │ └── MusicaResponseDTO.java/<br>
| | │ │ └── PlaylistRequestDTO.java/<br>
| | │ │ └── PlaylistResponseDTO.java/<br>
| | │ │ └── UsuarioRequestDTO.java/<br>
| | │ │ └── UsuarioResponseDTO.java/<br>
| | │ └── model/<br>
| | │ │ └── Genero.java/<br>
| | │ │ └── Musica.java/<br>
| | │ │ └── Playlist.java/<br>
| | │ │ └── Usuario.java/<br>
| | │ └── repository/<br>
| | │ │ └── GeneroRepository.java<br>
| | │ │ └── MusicaRepository.java<br>
| | │ │ └── PlaylistRepository.java<br>
| | │ │ └── UsuarioRepository.java<br>
| | │ └── security/<br>
| | │ │ └── jwt/<br>
| | │ │ | └── JwtAuthenticationFilter.java/<br>
| | │ │ | └── JwtUtil.java/<br>
| | │ │ └── service/<br>
| | │ │ | └── UsuarioDetailsService.java/<br>
| | │ └── SecurityConfig.java/<br>
| | │ └── SwaggerConfig.java/<br>
| | │ └── service/<br>
| | │ │ └── GeneroService.java/<br>
| | │ │ └── MusicaService.java/<br>
| | │ │ └── PlaylistService.java/<br>
| | │ │ └── UsuarioService.java/<br>
| | │ └── MiniSpotifyApplication.java/<br>
| │ └── resources/<br>
| | │ └── _data.sql<br>
| | │ └── application.properties<br>
| | │ └── application-dev.properties<br>
| | │ └── application-prod.properties<br>
│ └── test/java/com/microservico_java/mini_spotify<br>
| │ └── bootstrap/<br>
| | │ └── DataLoaderTest.java<br>
| │ └── controller/<br>
| | │ └── GeneroControllerTest.java<br>
| | │ └── MusicaControllerTest.java<br>
| | │ └── UsuarioControllerTest.java<br>
| │ └── exception/<br>
| | │ └── GlobalExceptionHandlerTest.java<br>
| │ └── service/<br>
| | │ └── GeneroServiceTest.java<br>
| | │ └── MusicaServiceTest.java<br>
| | │ └── UsuarioServiceTest.java<br>
| │ └── security/<br>
| | │ └── SecurityConfigTest.java<br>
| | │ └── service/<br>
| | │ │ └── UsuarioDetailsServiceTest.java/<br>
| | │ └── jwt/<br>
| | │ │ └── JwtAuthenticationEntryPointTest.java/<br>
| | │ │ └── JwtAuthenticationFilterTest.java/<br>
| | │ │ └── JwtUtilTest.java/<br>
| │ └── MiniSpotifyApplicationTests.java<br>
├── .env.example<br>
├── .gitattributes<br>
├── .gitignore<br>
├── README.md<br>
├── mvnw<br>
├── mvnw.cmd<br>
└── pom.xml<br>

