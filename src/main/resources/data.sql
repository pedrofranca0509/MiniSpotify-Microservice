-- Garante que existem usuários para vincular
INSERT INTO usuario (id, email, senha) VALUES
(1, 'usuario@teste.com', 'senha123');

-- Playlists iniciais
INSERT INTO playlist (id, nome, usuario_id) VALUES
(1, 'Rock', 1),
(2, 'Pop', 1);
