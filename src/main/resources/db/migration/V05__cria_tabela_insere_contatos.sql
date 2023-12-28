CREATE TABLE IF NOT EXISTS contato (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    email VARCHAR(40) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    departamento VARCHAR(30),
    id_cliente INT NOT NULL,
    FOREIGN key(id_cliente) REFERENCES cliente(id)
) ENGINE=InnoDB;
