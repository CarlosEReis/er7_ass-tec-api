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

INSERT INTO permissao (id, nome) values
(1, 'ROLE_ADMIN'),
(2, 'ROLE_GESTOR'),
(3, 'ROLE_TECNICO');
