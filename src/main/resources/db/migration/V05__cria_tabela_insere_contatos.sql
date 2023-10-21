CREATE TABLE IF NOT EXISTS contato (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    email VARCHAR(40) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    departamento VARCHAR(30),
    id_cliente INT NOT NULL,
    FOREIGN key(id_cliente) REFERENCES cliente(id)
) ENGINE=InnoDB;

INSERT INTO contato (nome, email, telefone, departamento, id_cliente) VALUES 
('Lucinea Rubi Bravo', 'lucinea.bravo@geradornv.com.br', '61981282645', 'Engenharia', 1),
('Antonieta Barboza Folly', 'antonieta.folly@geradornv.com.br', '77991190867', 'Engenharia', 1);