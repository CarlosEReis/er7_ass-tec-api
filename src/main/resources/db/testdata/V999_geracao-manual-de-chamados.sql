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


CREATE PROCEDURE fabrica_de_chamados(IN p_ano_ini INT, IN p_ano_fim INT, IN p_mes_ini INT, IN p_mes_fim INT)
BEGIN
  DECLARE a INT;
  DECLARE m INT;
  DECLARE d INT;
  DECLARE dia_fim INT;

  SET a = p_ano_ini;
  WHILE a <= p_ano_fim DO
    IF a = p_ano_ini THEN
      SET m = p_mes_ini;
    ELSE
      SET m = 1;
    END IF;

    WHILE m <= IF(a = p_ano_fim, p_mes_fim, 12) DO
      IF a = YEAR(CURDATE()) AND m = MONTH(CURDATE()) THEN
        SET dia_fim = DAY(CURDATE());
      ELSE
        SET dia_fim = DAY(LAST_DAY(STR_TO_DATE(CONCAT(a, '-', m, '-01'), '%Y-%m-%d')));
      END IF;

      SET d = 1;
      WHILE d <= dia_fim DO
        CALL insert_qtde_chamados_entre(DATE(CONCAT(a, '-', LPAD(m, 2, '0'), '-', LPAD(d, 2, '0'))), 15, 30);
        SET d = d + 1;
      END WHILE;

      SET m = m + 1;
    END WHILE;

    SET a = a + 1;
  END WHILE;
END$$

CALL fabrica_de_chamados(2023, 2025, 1, 12);