-- Caso ao chamar example_for_loop() você tiver um erro de timeout, siga o procedimento abaixo:
-- Workbench Edit → Preferences → SQL Editor
-- DBMS connection read timeout interval (in seconds) = 0
-- Não se esqueça de após executar com sucesso, retornar o valor padrão

-- '2023-12-01'
DELIMITER $$
/**
Insere um e somente um chamado técnico com base em uma data e um status ('F', 'P', 'C').
'F' = FILA
'P' = PROCESSANDO
'C' = CONCLUIDO
**/
CREATE PROCEDURE insert_chamado(IN p_dtcriacao DATE, IN p_status CHAR(1))
BEGIN
    DECLARE idChamado INT;
    DECLARE idOcorrencia INT;

	-- Denife a quantidade total de cliente e produtos para sorteio
    DECLARE qtdeCliente INT DEFAULT (SELECT COUNT(id) FROM cliente);
    DECLARE qtdeProdutos INT DEFAULT (SELECT COUNT(id) FROM produto);

    -- AJUSTAR PARA DINAMICO
    SET @usuario = ELT(FLOOR(RAND() * 3 +1), 'Administrador', 'Gestor', 'Técnico');

    INSERT INTO chamado (dtcriacao, status, id_cliente) VALUES (p_dtcriacao, 'FILA', (FLOOR(RAND() * qtdeCliente) + 1));
    SET idChamado = LAST_INSERT_ID();

    INSERT INTO ocorrencia_ct (serial, id_produto, id_chamado, descricao) VALUES ('35810742459021', FLOOR(RAND() * qtdeProdutos) + 1, idChamado, 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium');
    SET idOcorrencia = LAST_INSERT_ID();

    IF p_status NOT IN ('F', 'P', 'C') THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Status inválido';
    END IF;

    IF p_status = 'F' THEN
        INSERT INTO status (data, status_type, usuario, status, id_chamado, id_item_chamado) VALUES
        (p_dtcriacao, 'CHAMADO', @usuario, 'FILA', idChamado, NULL),
        (p_dtcriacao, 'ITEM', @usuario, 'PENDENTE', NULL, idOcorrencia);
    END IF;

    IF p_status = 'P' THEN
        INSERT INTO status (data, status_type, usuario, status, id_chamado, id_item_chamado) VALUES
        (p_dtcriacao, 'CHAMADO', @usuario, 'FILA', idChamado, NULL),
        (p_dtcriacao, 'ITEM', @usuario, 'PENDENTE', NULL, idOcorrencia);

        INSERT INTO status (data, status_type, usuario, status, id_chamado, id_item_chamado) VALUES
        (p_dtcriacao, 'CHAMADO', @usuario, 'PROCESSANDO', idChamado, NULL),
        (p_dtcriacao, 'ITEM', @usuario, 'AVALIANDO', NULL, idOcorrencia);

        UPDATE chamado c SET c.status = 'PROCESSANDO' WHERE c.id = idChamado;
    END IF;

    IF p_status = 'C' THEN
        INSERT INTO status (data, status_type, usuario, status, id_chamado, id_item_chamado) VALUES
        (p_dtcriacao, 'CHAMADO', 'Administrador', 'FILA', idChamado, NULL),
        (p_dtcriacao, 'ITEM', @usuario, 'PENDENTE', NULL, idOcorrencia);

        INSERT INTO status (data, status_type, usuario, status, id_chamado, id_item_chamado) VALUES
        (p_dtcriacao, 'CHAMADO', @usuario, 'PROCESSANDO', idChamado, NULL),
        (p_dtcriacao, 'ITEM', @usuario, 'AVALIANDO', NULL, idOcorrencia);

        INSERT INTO status (data, status_type, usuario, status, id_chamado, id_item_chamado) VALUES
        (p_dtcriacao, 'CHAMADO', @usuario, 'FINALIZADO', idChamado, NULL),
        (p_dtcriacao, 'ITEM', @usuario, 'AVALIADO', NULL, idOcorrencia);

        UPDATE chamado c SET c.status = 'FINALIZADO' WHERE c.id = idChamado;
    END IF;

END;


/**
Insere uma quantidade X de chamados técnicos que é definida por número aleatório
entre um valor mínimo (min_value) e máximo (max_value), em uma data específica.
**/
CREATE PROCEDURE insert_qtde_chamados_entre(IN p_dtcriacao DATE, IN min_value INT, IN max_value INT)
BEGIN
  -- Define a quantidade de chamados que serão criados
  SET @random_number = FLOOR(RAND() * (max_value - min_value + 1) + min_value);
  WHILE @random_number > 0 DO
    CALL insert_chamado(p_dtcriacao, ELT(FLOOR(RAND() * 3 +1), 'F', 'P', 'C'));
    SET @random_number = @random_number - 1;
  END WHILE;
END;


CREATE PROCEDURE fabrica_de_chamados()
BEGIN
  DECLARE y INT DEFAULT 1;
  DECLARE init_count INT DEFAULT 2023;
  DECLARE max_count INT DEFAULT 2023;

  DECLARE m INT DEFAULT 1;
  DECLARE mes_init INT DEFAULT 1;
  DECLARE mes_fim INT DEFAULT 12;

  DECLARE d INT DEFAULT 1;
  DECLARE dia_init INT DEFAULT 01;
  DECLARE dia_fim INT DEFAULT 30;

  FOR y IN init_count..max_count DO
    FOR m IN mes_init..mes_fim DO
        IF m = 2 THEN
            SET dia_fim = dia_fim -2;
        END if;
        FOR d IN dia_init..dia_fim DO
            CALL insert_qtde_chamados_entre(DATE(CONCAT(y,LPAD(m, 2, '0'),LPAD(d, 2, '0'))), 15, 30);
        END FOR;
    END FOR;
  END FOR;
END$$

CALL fabrica_de_chamados();