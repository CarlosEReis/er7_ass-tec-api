CREATE TABLE IF NOT EXISTS chamado_tec (
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
    descricao VARCHAR(400) NOT NULL,
    id_chamado INT NOT NULL,
    FOREIGN KEY (id_chamado) REFERENCES chamado_tec(id)
) Engine=InnoDb;

INSERT INTO chamado_tec (dtcriacao, status,id_cliente) VALUES 
(now(), 'FILA', 1),
(now(), 'PROCESSANDO', 2),
(now(), 'FINALIZADO', 2);

INSERT INTO ocorrencia_ct (sku, status,serial, id_chamado, descricao) VALUES 
('ER3001','PENDENTE','35810742459021', 1,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('ER839800-02','AVALIANDO','44554443583669', 1,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('ER727','PENDENTE','58533909538718', 2,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('ER7662','AVALIANDO','35051414240058', 2,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa');

