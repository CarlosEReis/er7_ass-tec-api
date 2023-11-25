CREATE TABLE IF NOT EXISTS usuario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(30) NOT NULL,
    senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS permissao (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL
) Engine=InnoDB;

CREATE TABLE IF NOT EXISTS usuario_permissao (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_permissao INT NOT NULL,

    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    FOREIGN KEY (id_permissao) REFERENCES permissao(id)
) ENGINE=InnoDB;

INSERT INTO usuario (id, nome, email, senha) values
(1, 'Administrador', 'admin@er7sistemas.com', '$2y$10$cl2nIPtGyvPGbs9bjzx08ewt/DQ2JHqTftsKGBJIrraHu0vBX/nTu'),
(2, 'Gestor', 'gestor@er7sistemas.com', '$2y$10$d36V9fcBi38GYOHiOTNW2uVyOtwoBxwoPnZpbk5p9zLKpuEIjT38.'),
(3, 'Técnico', 'tecnico@er7sistemas.com', '$2y$10$bqO6iDyzX9U9tn3MXx/HKe5Df.jPfIpktLo1QQXmkvVC1x4Gzegs2');

INSERT INTO permissao (id, nome) values
(1, 'ROLE_ADMIN'),
(2, 'ROLE_GESTOR'),
(3, 'ROLE_TECNICO');

INSERT INTO usuario_permissao ( id, id_usuario, id_permissao) values
(1, 1, 1),
(2, 2, 2),
(3, 3, 3);

