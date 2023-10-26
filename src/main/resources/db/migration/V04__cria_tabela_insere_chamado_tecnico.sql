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

INSERT INTO chamado (id, dtcriacao, status,id_cliente) VALUES 
(1, '2023-08-30 11:14:29', 'FILA', 1),
(2, '2023-08-31 10:11:29', 'FILA', 2),
(3, '2023-08-31 16:18:29', 'FILA', 2);

INSERT INTO ocorrencia_ct (serial, id_produto, id_chamado, descricao) VALUES
('35810742459021', 29, 1,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('44554443583669', 75, 1,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('58533909538718', 43, 2,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('35051414240058', 63, 3,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa');


INSERT INTO status
(id, data, status_type, usuario, status, id_chamado, id_item_chamado) VALUES
(1,'2023-08-30 11:14:29','ITEM','Administrador','PENDENTE',NULL,1),
(2,'2023-08-30 11:14:29','ITEM','Administrador','PENDENTE',NULL,2),
(3,'2023-08-30 11:14:29','CHAMADO','Administrador','FILA',1,NULL),
(4,'2023-08-31 10:11:29','ITEM','Administrador','PENDENTE',NULL,3),
(5,'2023-08-31 10:11:29','CHAMADO','Administrador','FILA',2,NULL),
(6,'2023-08-31 16:18:29','ITEM','Administrador','PENDENTE',NULL,4),
(7,'2023-08-31 16:18:29','CHAMADO','Administrador','FILA',3,NULL);
