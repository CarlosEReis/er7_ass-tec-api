CREATE TABLE IF NOT EXISTS cliente (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(40) NOT NULL,
    fantasia VARCHAR(30),
    documento VARCHAR(14) NOT NULL,
    inscricao VARCHAR(18),
    tabela_preco INT,
    email VARCHAR(40) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    tipo_pessoa ENUM('FISICA', 'JURIDICA') NOT NULL
) ENGINE=InnoDB;
