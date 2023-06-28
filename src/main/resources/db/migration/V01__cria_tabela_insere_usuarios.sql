CREATE TABLE IF NOT EXISTS usuario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL
) ENGINE=InnoDB;

INSERT INTO usuario (nome) VALUES ('Ana'), ('Bianca'), ('Carla'), ('Denise'), ('Fernanda');