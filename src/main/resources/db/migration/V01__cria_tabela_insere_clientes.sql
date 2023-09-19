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

INSERT INTO cliente (id, nome, fantasia, documento, inscricao, tabela_preco, email, telefone, tipo_pessoa) VALUES 
(1, 'Jose Moraes Melo', NULL, '92326765834', NULL, NULL, 'antonio@antonio.com.br', '(95) 67904-7031', 'FISICA'),
(2, 'Amaral Bragança Planejamento ME', 'Amaral Planejamento', '66526565000115', '75.791.206-0', NULL, 'planejamento.amaral@geradornv.com.br', '(97) 3861-3757', 'JURIDICA')