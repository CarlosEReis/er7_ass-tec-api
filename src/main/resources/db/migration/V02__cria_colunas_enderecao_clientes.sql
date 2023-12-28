ALTER TABLE cliente 
ADD COLUMN (
	cep VARCHAR(9),
	logradouro VARCHAR(40),
    numero VARCHAR(30),
    complemento VARCHAR(30),
    bairro VARCHAR(40),
    cidade VARCHAR(40),
    estado VARCHAR(30)
);
