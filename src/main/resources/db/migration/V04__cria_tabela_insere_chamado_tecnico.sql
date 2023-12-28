CREATE TABLE IF NOT EXISTS chamado (
	id INT PRIMARY KEY AUTO_INCREMENT,
    dtcriacao DATETIME NOT NULL,
    status ENUM('FILA', 'PROCESSANDO', 'FINALIZADO'),
    id_cliente INT NOT NULL,
    FOREIGN KEY(id_cliente) REFERENCES cliente(id)
) Engine=InnoDb;

CREATE TABLE IF NOT EXISTS ocorrencia_ct (
	id INT PRIMARY KEY AUTO_INCREMENT,
    serial VARCHAR(50),
    descricao TEXT NOT NULL,
    id_chamado INT NOT NULL,
    id_produto INT NOT NULL,
    FOREIGN KEY (id_chamado) REFERENCES chamado(id),
    FOREIGN KEY (id_produto) REFERENCES produto(id)
) Engine=InnoDb;

CREATE TABLE status (
	id INT PRIMARY KEY AUTO_INCREMENT,
    data DATETIME NOT NULL,
    status_type VARCHAR(30),
    usuario VARCHAR(50),
    status ENUM('FILA', 'PROCESSANDO', 'FINALIZADO', 'PENDENTE', 'AVALIANDO', 'AVALIADO'),
	id_chamado INT,
    id_item_chamado INT,
    FOREIGN KEY (id_chamado) REFERENCES chamado(id),
    FOREIGN KEY (id_item_chamado) REFERENCES ocorrencia_ct(id)
) ENGINE=InnoDB;
