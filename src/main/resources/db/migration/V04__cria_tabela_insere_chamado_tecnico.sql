CREATE TABLE IF NOT EXISTS chamado (
	id INT PRIMARY KEY AUTO_INCREMENT,
    dtcriacao DATETIME NOT NULL,
    status ENUM('FILA', 'PROCESSANDO', 'FINALIZADO'),
    id_cliente INT NOT NULL,
    FOREIGN KEY(id_cliente) REFERENCES cliente(id)
) Engine=InnoDb;

CREATE TABLE IF NOT EXISTS ocorrencia_ct (
	id INT PRIMARY KEY AUTO_INCREMENT,
    status ENUM('PENDENTE', 'AVALIANDO', 'AVALIADO') NOT NULL,
    sku VARCHAR(15) NOT NULL,
    serial VARCHAR(50),
    descricao TEXT NOT NULL,
    id_chamado INT NOT NULL,
    FOREIGN KEY (id_chamado) REFERENCES chamado(id)
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

CREATE TABLE ocorrencia_ct_status (
    id_item_chamado INT NOT NULL,
    id_status INT NOT NULL,

    FOREIGN KEY (id_item_chamado) REFERENCES ocorrencia_ct(id),
    FOREIGN KEY (id_status) REFERENCES status(id)
) Engine=InnoDb;

INSERT INTO chamado (id, dtcriacao, status,id_cliente) VALUES 
(1, '2023-08-30 11:14:29', 'FILA', 1),
(2, '2023-08-31 10:11:29', 'PROCESSANDO', 2),
(3, '2023-08-31 16:18:29', 'FINALIZADO', 2);

INSERT INTO ocorrencia_ct (sku, status,serial, id_chamado, descricao) VALUES 
('ER3001','PENDENTE','35810742459021', 1,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('ER839800-02','AVALIANDO','44554443583669', 1,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('ER727','PENDENTE','58533909538718', 2,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('ER7662','AVALIANDO','35051414240058', 3,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa');

INSERT INTO status (id, data, status_type, usuario, status, id_chamado) VALUES
(1, '2023-09-26 01:32:55.501835', 'ITEM', 'Administrador', 'PENDENTE', 3),
(2, '2023-09-26 01:32:55.501864', 'ITEM', 'Administrador', 'PENDENTE', 3),
(3, '2023-09-26 01:32:55.501877', 'CHAMADO', 'Administrador', 'FILA', 3);

