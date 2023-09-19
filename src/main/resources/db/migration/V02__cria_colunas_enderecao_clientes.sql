ALTER TABLE cliente 
ADD COLUMN (
	cep VARCHAR(9) NOT NULL,
	logradouro VARCHAR(40),
    numero VARCHAR(30),
    complemento VARCHAR(30),
    bairro VARCHAR(40) NOT NULL,
    cidade VARCHAR(40) NOT NULL,
    estado VARCHAR(30) NOT NULL
);

UPDATE cliente
	SET cep ='59068-450', logradouro ='Rua do Sobreiro', numero='16', complemento='Apto 15', bairro='Pitimbu', cidade='Natal', estado='RN' WHERE cliente.id = 1;
    
UPDATE cliente
    SET cep ='71640035', logradouro ='Quadra SHIS QL 14 Conjunto 3', numero='15', complemento='Conjutno 14', bairro='Setor de Habitações Individuais Sul', cidade='Brasília', estado='DF' WHERE cliente.id = 2;

