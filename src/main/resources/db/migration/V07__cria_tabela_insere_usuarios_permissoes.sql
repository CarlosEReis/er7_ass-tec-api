CREATE TABLE IF NOT EXISTS usuario (
    id INT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(30) NOT NULL,
    senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS permissao (
    id INT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
) Engine=InnoDB;

CREATE TABLE IF NOT EXISTS usuario_permissao (
    id INT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_permissao INT NOT NULL,

    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    FOREIGN KEY (id_permissao) REFERENCES permissao(id)
) ENGINE=InnoDB;

INSERT INTO usuario (id, nome, email, senha) values
(1, 'Administrador', 'admin@er7sistemas.com', '$2y$10$vqqB/1NyLFK0NTVofmfELOqlpPkx1V8Cj2pqj/lJdHMUlalpuGPOW'),
(2, 'Usuario', 'user@er7sistemas.com', '$2y$10$60zn8XcyS89RWTs.i/FuTudXIy8qafeNhGTfbrU//.SmF4wfoFV52');

INSERT INTO permissao (id, nome) values
(1, 'ROLE_WRITE'),
(2, 'ROLE_READ');

INSERT INTO usuario_permissao ( id, id_usuario, id_permissao) values
(1, 1, 1),
(2, 1, 2),
(3, 2, 2);

