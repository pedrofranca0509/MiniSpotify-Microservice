-- Primeiro limpe as tabelas (importante!)
DELETE FROM playlist_musica;
DELETE FROM playlist;
DELETE FROM usuario;

-- Depois insira os dados iniciais
INSERT INTO usuario (id, email, senha) VALUES
(1, 'usuario@teste.com', 'senha123');

-- Use ID NULL para que o Hibernate gere os IDs automaticamente
INSERT INTO playlist (nome, usuario_id) VALUES
('Rock Classico', 1),
('Pop Internacional', 1);
