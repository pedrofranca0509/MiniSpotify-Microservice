DELETE FROM playlist_musica;
DELETE FROM playlist;
DELETE FROM usuario;

INSERT INTO usuario (id, email, senha) VALUES
(1, 'usuario@teste.com', 'senha123');

INSERT INTO playlist (nome, usuario_id) VALUES
('Rock Classico', 1),
('Pop Internacional', 1);
