DELETE FROM playlist_musica;
DELETE FROM playlist;
DELETE FROM musica;
DELETE FROM genero;
DELETE FROM album;
DELETE FROM usuario;

INSERT INTO usuario (id, email, nome, senha) VALUES
(1, 'usuario@teste.com', 'userTeste', 'senha123');

INSERT INTO genero (id, nome) VALUES 
(1, 'NomeGenero');

INSERT INTO album (id, ano_lancamento, artista, nome) VALUES 
(1, 2000,'AutorAlbum', 'NomeAlbum');

INSERT INTO musica (id, artista, duracao_segundos, titulo, album_id, genero_id) VALUES
(1, 'NomeArtista', 354,'NomeMusica', 1, 1);

INSERT INTO playlist (id, nome, usuario_id) VALUES
(3, 'NomePlaylist', 1),
(4, 'NomePlaylist2', 1);

INSERT INTO playlist_musica (playlist_id, musica_id) VALUES
(3, 1); 

