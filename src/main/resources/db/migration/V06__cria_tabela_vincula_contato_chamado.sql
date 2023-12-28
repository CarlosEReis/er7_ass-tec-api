CREATE TABLE IF NOT EXISTS chamado_contato (
    id_chamado INT NOT NULL,
    id_contato INT NOT NULL,

    PRIMARY KEY (id_chamado, id_contato),
    CONSTRAINT `fk_chamado_id` 
        FOREIGN KEY (id_chamado) REFERENCES chamado(id),
    CONSTRAINT `fk_contato_id` 
        FOREIGN KEY (id_contato) REFERENCES contato(id)
) Engine=InnoDB;
