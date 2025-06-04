DELETE FROM playlist_musica;
DELETE FROM playlist;
DELETE FROM musica;
DELETE FROM genero;
DELETE FROM usuario;

-- Usuário sem ID
INSERT INTO usuario (email, nome, senha) VALUES
('usuario@teste.com', 'userTeste', 'senha123');

-- Gênero sem ID
INSERT INTO genero (nome) VALUES 
('NomeGenero');


-- Música sem ID (precisa do ID do gênero - veja observação abaixo)
-- Aqui você precisará obter os IDs com subqueries
INSERT INTO musica (artista, duracao_segundos, titulo, genero_id) VALUES
('NomeArtista', 354, 'NomeMusica',
 (SELECT id FROM genero WHERE nome = 'NomeGenero')
);

-- Playlist sem ID (usando SELECT para pegar o ID do usuário)
INSERT INTO playlist (nome, usuario_id) VALUES
('NomePlaylist', (SELECT id FROM usuario WHERE email = 'usuario@teste.com')),
('NomePlaylist2', (SELECT id FROM usuario WHERE email = 'usuario@teste.com'));

-- Playlist-música (usando SELECT para pegar os IDs)
INSERT INTO playlist_musica (playlist_id, musica_id) VALUES
(
  (SELECT id FROM playlist WHERE nome = 'NomePlaylist'),
  (SELECT id FROM musica WHERE titulo = 'NomeMusica')
);
