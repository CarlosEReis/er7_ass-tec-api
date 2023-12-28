INSERT INTO cliente (id, nome, fantasia, documento, inscricao, tabela_preco, email, telefone, tipo_pessoa) VALUES
(1, 'Jose Moraes Melo', NULL, '92326765834', NULL, NULL, 'antonio@antonio.com.br', '95679047031', 'FISICA'),
(2, 'Amaral Bragança Planejamento ME', 'Amaral Planejamento', '66526565000115', '75.791.206-0', NULL, 'planejamento.amaral@geradornv.com.br', '9738613757', 'JURIDICA');

UPDATE cliente
	SET cep ='59068450', logradouro ='Rua do Sobreiro', numero='16', complemento='Apto 15', bairro='Pitimbu', cidade='Natal', estado='RN' WHERE cliente.id = 1;

UPDATE cliente
    SET cep ='71640035', logradouro ='Quadra SHIS QL 14 Conjunto 3', numero='15', complemento='Conjutno 14', bairro='Setor de Habitações Individuais Sul', cidade='Brasília', estado='DF' WHERE cliente.id = 2;


INSERT INTO produto (sku, nome) VALUES
("CL1.5-2","BLACKSTONECHEMICALDOSINGPUMP")
,("CL10-2","BLACKSTONECHEMICALDOSINGPUMP")
,("CL15-2","BLACKSTONECHEMICALDOSINGPUMP")
,("CL20-1","BLACKSTONECHEMICALDOSINGPUMP")
,("CL20-2","BLACKSTONECHEMICALDOSINGPUMP")
,("CL931700-1","PHMINICONTROLLER")
,("CL932700-1","ORPMINICONTROLLER")
,("CL981411-0","PHMINICONTROLLER")
,("CL981411-1","PHMINICONTROLLER")
,("CL982411-1","ORPMINICONTROLLER")
,("CL983313-1","CONDUCTIVITYMINICONTROLLER")
,("CL983317-1","CONDUCTIVITYMINICONTROLLERMEASURINGINMSCM")
,("CL983320-1","CONDUCTIVITYMINICONTROLLER")
,("CL983327-1","CONDUCTIVITYMINICONTROLLERMEASURINGINMSCM")
,("CF099","STAINLESSSTEELBLADEFORMEATPHELECTRODE")
,("ER1001","PHELECTRODE")
,("ER1285-5","PHCONDUCTIVITYPROBE")
,("ER144-10","T-LOGGERWITHLOCKINGWALLCRADLE")
,("ER145-00","T-SHAPEDCTHERMOMETER")
,("ER145-20","T-SHAPEDCTHERMOMETER")
,("ER147-00","CHECKFRIDGECREMOTESENSORTHERMOMETER")
,("ER151-00","CHECKTEMP4CFOLDINGPOCKETTERMOMETER")
,("ER181F-2","COMPACTMAGNETICMINI-STIRRERWITHELECTRODEHOLDER")
,("ER181K-1","COMPACTMAGNETICMINI-STIRRERWITHELECTRODEHOLDER")
,("ER181W-2","COMPACTMAGNETICMINI-STIRRERWITHELECTRODEHOLDER")
,("ER190M-1","MAGNETICMINI-STIRRER")
,("ER190M-2","MAGNETICMINI-STIRRER")
,("ER2001","ORPELECTRODE")
,("ER3001","CONDUCTIVITYPROBE")
,("ER504224-2","PHORPDIGITALCONTROLLERWITHSENSORCHECK")
,("ER6051","SUBMERSIBLEELECTRODEHOLDER")
,("ER6100405","INDUSTRIALPHELECTRODE")
,("ER6101405","INDUSTRIALPHELECTRODE")
,("ER6101415","INDUSTRIALPHELECTRODE")
,("ER6291005","AMPHELPHELECTRODE")
,("ER70483","PCACOMPLETETUBINGKIT")
,("ER70484","PCACOMPLETETUBINGKIT(3SETS)")
,("ER70486","PCASTIRRINGBAR(2PCS)")
,("ER710006","POWERADAPTER-230VACTO12VDC,EUROPEANPLUG")
,("ER710025","SHOCKPROOFBOOT,GREEN")
,("ER710027","PROTECTRUBBERBOOTFORT8METERWITHRECTANGULARHOLE")
,("ER721102","DISCHARGEVALVEASSEMBLY")
,("ER727","HANDHELDCOLORIMETERCOLOROFWATER")
,("ER731225","CAPSFORCHECKERHCTMCUVETTES(4)")
,("ER73127","PHELECTRODE")
,("ER731318","BLUECLEANINGCLOTHSFORWIPINGCUVETTES(4PCS)")
,("ER731321","GLASSCUVETTEFORHI832XXSERIES(4PCS)")
,("ER731325","CAPFORCUVETTEFORTURBIDITYMETERS(4PCS)")
,("ER731331","GLASSCUVETTEFORHI96SERIES(4PCS)")
,("ER731339P","0.1MLMINIPIPETTE")
,("ER73311","CONDUCTIVITYPROBE")
,("ER740216","TESTTUBECOOLINGRACK(25HOLES)")
,("ER740233","FILTERPAPERTYPE2(100PCS)")
,("ER740234","REPLACEMENTLAMPFOREPATURBIDIMETER")
,("ER7609829-1","PHORPSENSOR")
,("ER7609829-2","DOSENSOR")
,("ER76303","CONDUCTIVITYPROBE")
,("ER763100","CONDUCTIVITYPROBE")
,("ER7632-00","CONDUCTIVITYPROBE")
,("ER7634-00","CONDUCTIVITYPROBE")
,("ER7635","4-PININLINECONDUCTIVITYPROBE")
,("ER76409A/P","DOMEMBRANES")
,("ER7662","TEMPERATUREPROBE")
,("ER766E1","K-TYPEPENETRATIONPROBEFORLIQUID,AIR&GASES")
,("ER766TR2","K-TYPETEMPERATUREPROBE")
,("ER7698194","MULTIPARAMETERPROBEFORPHORPECDOTEMPERATURE,4MCABLE")
,("ER7698194-1","PHORPSENSOR")
,("ER7698194-2","DOSENSOR")
,("ER7698194-3","ECSENSOR")
,("ER769828-1","PHORPSENSOR")
,("ER769828-2","DOSENSOR")
,("ER769828-3","ECSENSOR")
,("ER7698294","FLOWCELL")
,("ER839800-01","CODTESTTUBEHEATERWITH25VIALSCAPACITY")
,("ER839800-02","CODTESTTUBEHEATERWITH25VIALSCAPACITY")
,("ER8936CN","CONDUCTIVITYTRANSMITTERTOUSEWITHFOUR-RINGPROBE")
,("ER935001","K-TYPETHERMOCOUPLETHERMOMETER")
,("ER935005","K-TYPETHERMOCOUPLETHERMOMETER")
,("ER93501","THERMISTORTHERMOMETER")
,("ER935012","THERMISTORTHERMOMETER")
,("ER93703-60","CAPSFORCUVETTES(4)")
,("ER9564","WATERPROOFPORTABLETHERMOHYGROMETER")
,("ER9565","WATERPROOFPORTABLEDEWPOINTHYGROMETER")
,("ER96710","FREE&TOTALCHLORINEANDPHPORTABLEPHOTOMETER")
,("ER96723","CHROMIUMVIHIGHRANGEPORTABLEPHOTOMETER")
,("ER96735","TOTALHARDNESSEPAPORTABLEPHOTOMETER")
,("ER96737","SILVERPORTABLEPHOTOMETER")
,("ER96800","DIGITALREFRACTOMETERFORREFRACTIVEINDEXANDBRIXMEASUREMENTS")
,("ER96801","DIGITALREFRACTOMETERFORSUGARANALYSIS")
,("ER96822","DIGITALREFRACTOMETERFORNATURALORARTIFICIALSEAWATERANALYSIS")
,("ER96831","DIGITALREFRACTOMETERFORETHYLENEGLYCOLANALYSIS")
,("ER96841","DIGITALREFRACTOMETERFORBEERMEASURMENTS")
,("ER97500","PORTABLELUXMETER")
,("ER98100","CHECKERPLUS-PHTESTER")
,("ER98103","CHECKERPHTESTER")
,("ER981030","GROLINESOILPHTESTER")
,("ER981031","FOODCAREBEERPHTESTER")
,("ER981032","FOODCARECHEESEPHTESTER")
,("ER981034","FOODCAREMILKPHTESTER")
,("ER981036","FOODCAREMEATPHTESTER")
,("ER98107","POCKETSIZEDPHMETER")
,("ER98108","POCKETSIZEDPHMETER")
,("ER98115","GROLINE-PHTESTER")
,("ER98118","GROLINEPHWATERPROOFTESTER")
,("ER98120","WATERPROOFORPTESTER")
,("ER98121","WATERPROOFPHORPTESTER")
,("ER98127","PHEP4PHTEMPERATURETESTER")
,("ER98128","PHEP5PHTEMPERATURETESTER")
,("ER98129","PHCONDUCTIVITYTDSTESTER")
,("ER98130","PHCONDUCTIVITYTDSTESTER")
,("ER98131","GROLINEPHECTDSCOMBOTESTER")
,("ER98143-22","PHANDECTRANSMITTERWITHGALVANICISOLATEDOUTPUT")
,("ER98302","DIST2TDSTESTER")
,("ER98303","DIST3ECTESTER")
,("ER98304","DIST4ECTESTER")
,("ER98308","PUREWATERTESTTESTER")
,("ER98311","ECTDSTEMPERATURETESTER")
,("ER98312","ECTDSTEMPERATURETESTER")
,("ER98318","GROLINEWATERPROOFECTDSTESTER")
,("ER983304-02","CONDUCTIVITYMETERFORDEMINERALIZEDWATER")
,("ER98331","GROLINEDIRECTSOILCONDUCTIVITYTESTER")
,("ER98501","CHECKTEMPELECTRONICTHERMOMETER")
,("ER98509","CHECKTEMPELECTRONICTHERMOMETER")
,("PS83099-1","DISPLAYBOARDFORHI83099")
,("PS83099-2","POWERBOARDFORHI83099")
,("PS839800-10","HEATINGCUVETTESUPPORTSYSTEMFORHI839800")
,("PS9126-1","MAINBOARDFORHI99121")
,("PS93703-10","COMPLETECASEWITHOPTICALSYSTEMFORHI93703");

INSERT INTO contato (nome, email, telefone, departamento, id_cliente) VALUES
('Lucinea Rubi Bravo', 'lucinea.bravo@geradornv.com.br', '61981282645', 'Engenharia', 1),
('Antonieta Barboza Folly', 'antonieta.folly@geradornv.com.br', '77991190867', 'Engenharia', 1);

INSERT INTO usuario (id, nome, email, senha) values
(1, 'Administrador', 'admin@er7sistemas.com', '$2y$10$cl2nIPtGyvPGbs9bjzx08ewt/DQ2JHqTftsKGBJIrraHu0vBX/nTu'),
(2, 'Gestor', 'gestor@er7sistemas.com', '$2y$10$d36V9fcBi38GYOHiOTNW2uVyOtwoBxwoPnZpbk5p9zLKpuEIjT38.'),
(3, 'Técnico', 'tecnico@er7sistemas.com', '$2y$10$bqO6iDyzX9U9tn3MXx/HKe5Df.jPfIpktLo1QQXmkvVC1x4Gzegs2');

/**INSERT INTO permissao (id, nome) values
(1, 'ROLE_ADMIN'),
(2, 'ROLE_GESTOR'),
(3, 'ROLE_TECNICO');**/

INSERT INTO usuario_permissao ( id, id_usuario, id_permissao) values
(1, 1, 1),
(2, 2, 2),
(3, 3, 3);

INSERT INTO cliente (id, nome, fantasia, documento, inscricao, tabela_preco, email, telefone, tipo_pessoa, cep, logradouro, numero, complemento, bairro, cidade, estado)
  VALUES
  (3, 'Braz Pontes Posto EPP', 'Posto Braz', '51419484000149', '1803909317-0', NULL, 'posto.braz@geradornv.com.br', '(63) 2525-6371', 'JURIDICA', '77064-144', 'Rua SF', '13', NULL, 'Setor Santa Fé 2 (Taquaralto)', 'Palmas', 'TO'),
  (4, 'Lucio Sá Tatuagens EPP', 'Tatuagens Lucio', '77686886000101', '036049107', NULL, 'tatuagens.lucio@geradornv.com.br', '(96) 2681-7842', 'JURIDICA', '68927-378', 'Travessa L17 do Provedor', '37', NULL, 'Santana', 'Provedor', 'AP'),
  (5, 'Passos Henriquez Atacado ME', 'Atacado Passos', '31813665000148', '05825701-2', NULL, 'atacado.passos@geradornv.com.br', '(28) 2169-6222', 'JURIDICA', '29179-300', 'Rua Andiroba Branca', '21', NULL, 'Residencial Centro da Serra', 'Serra', 'ES'),
  (6, 'Barcelos Bastida Propaganda LTDA', 'Propaganda Barcelos', '35112206000198', '414/1347425', NULL, 'propaganda.barcelos@geradornv.com.br', '(55) 3776-2822', 'JURIDICA', '95901-558', 'Rua Nossa Senhora da Conceição', '21', NULL, 'Conservas', 'Lajeado', 'RS'),
  (7, 'Carminatti Linhares Posto LTDA', 'Posto Carminatti', '32618405000184', '033.997.574.760', NULL, 'posto.carminatti@geradornv.com.br', '(15) 3134-8955', 'JURIDICA', '09190-660', 'Rua Jabaquara', '21', NULL, 'Paraíso', 'Santo André', 'SP'),
  (8, 'Cardoso Amancio Consultoria ME', 'Consultoria Cardoso', '23419636000178', '28359597-3', NULL, 'consultoria.cardoso@geradornv.com.br', '(67) 2674-8857', 'JURIDICA', '79118-610', 'Rua Santo Aleixo', '21', NULL, 'Bairro Seminário', 'Campo Grande', 'MS'),
  (9, 'Trancoso Navega Consultoria LTDA', 'Consultoria Trancoso', '66435398000105', '36016179-0', NULL, 'consultoria.trancoso@geradornv.com.br', '(27) 2566-3617', 'JURIDICA', '29155-342', 'Beco Silvano Ferreira Santos', '21', NULL, 'Porto Novo', 'Cariacica', 'ES'),
  (10, 'Alvarenga Avilla Contábil EPP', 'Contábil Alvarenga', '51461225000186', '99499095-2', NULL, 'contabil.alvarenga@geradornv.com.br', '(89) 2749-6392', 'JURIDICA', '64034-034', 'Rua da Miragem', '21', NULL, 'Angelim', 'Teresina', 'PI'),
  (11, 'Mayerhofer Antonio Pastelaria EPP', 'Pastelaria Mayerhofer', '21623551000181', '1354667350-5', NULL, 'pastelaria.mayerhofer@geradornv.com.br', '(65) 3225-4711', 'JURIDICA', '78135-221', 'Rua Chachau', '21', NULL, 'Canelas', 'Várzea Grande', 'MT'),
  (12, 'Barros Pacheco Perfumaria EPP', 'Perfumaria Barros', '58146789000173', '9497419-57', NULL, 'perfumaria.barros@geradornv.com.br', '(87) 3864-3749', 'JURIDICA', '53210-057', 'Rua Projetada', '21', NULL, 'Caixa DÁgua', 'Olinda', 'PE'),
  (13, 'Ubaldo Heizelmann Brechó ME', 'Brechó Ubaldo', '73282052000171', '606.881.184.367', NULL, 'brecho.ubaldo@geradornv.com.br', '(14) 2982-3214', 'JURIDICA', '17063-130', 'Rua Caetano Cariani', '21', NULL, 'Vila Gonçalves', 'Bauru', 'SP'),
  (14, 'Palmas Nunes Tintas LTDA', 'Tintas Palmas', '24547226000175', '80246884-5', NULL, 'tintas.palmas@geradornv.com.br', '(27) 2379-4315', 'JURIDICA', '29166-021', 'Rua Crateús', '21', NULL, 'Barcelona', 'Serra', 'ES'),
  (15, 'Jessica Stutz da Paixão', NULL, '94129025228', NULL, NULL, 'jessica.paixao@geradornv.com.br', '(69) 97422-1766', 'FISICA', '76961-814', 'Avenida Primavera', '21', NULL, 'Jardim Bandeirantes', 'Cacoal', 'RO'),
  (16, 'Selma Couto Farias', NULL, '33781177106', NULL, NULL, 'selma.farias@geradornv.com.br', '(61) 98829-8726', 'FISICA', '72161-202', 'Quadra QNL 22 Conjunto B', '21', NULL, 'Taguatinga Norte (Taguatinga)', 'Brasília', 'DF'),
  (17, 'João Batista Ximenes Paula', NULL, '34638328288', NULL, NULL, 'joao.paula@geradornv.com.br', '(69) 98235-2313', 'FISICA', '76876-544', 'Avenida Rio Branco', '21', NULL, 'Jardim Jorge Teixeira', 'Ariquemes', 'RO'),
  (18, 'Tânia Linhares Salles', NULL, '64618110872', NULL, NULL, 'tania.salles@geradornv.com.br', '(16) 97359-4787', 'FISICA', '13215-811', 'Alameda dos Ipês', '21', NULL, 'Rio Acima', 'Jundiaí', 'SP'),
  (19, 'Otoniel Esteves Zava', NULL, '42534727885', NULL, NULL, 'otoniel.zava@geradornv.com.br', '(12) 99395-9262', 'FISICA', '13601-386', 'Avenida Presidente Juscelino Kubitschek', '21', NULL, 'Conjunto Habitacional Narciso Gomes', 'Araras', 'SP'),
  (20, 'Benedito Pinho Falcão', NULL, '07028484840', NULL, NULL, 'benedito.falcao@geradornv.com.br', '(14) 99648-2348', 'FISICA', '17205-350', 'Rua Antônio Cipola', '21', NULL, 'Vila Industrial', 'Jaú', 'SP');

  INSERT INTO contato
  (nome, email, telefone, departamento, id_cliente) VALUES
  ('Georgiane Cordeiro Texeira', 'georgiane.texeira@geradornv.com.br', '(91) 99175-9690', 'Engenharia', 2),
  ('Emanuelle Henriquez Arlia', 'emanuelle.arlia@geradornv.com.br', '(96) 99642-0153', 'Engenharia', 3),
  ('Elias Silveira Laporte', 'elias.laporte@geradornv.com.br', '(27) 99594-4563', 'Engenharia', 4),
  ('Ignácio Guimarães Camara', 'ignacio.camara@geradornv.com.br', '(73) 98955-6571', 'Engenharia', 5),
  ('Pablo Prata Mota', 'pablo.mota@geradornv.com.br', '(85) 99996-0265', 'Engenharia', 6),
  ('Alfredo Camacho Amorin', 'alfredo.amorin@geradornv.com.br', '(79) 99682-8635', 'Engenharia', 7),
  ('Lucilene Elias Medeiros', 'lucilene.medeiros@geradornv.com.br', '(75) 98986-7611', 'Engenharia', 8),
  ('Elino Sá Gonçalves', 'elino.goncalves@geradornv.com.br', '(61) 98556-4676', 'Engenharia', 9),
  ('Cris de Jesus Fausto', 'cris.fausto@geradornv.com.br', '(93) 99356-8463', 'Engenharia', 10),
  ('Douglas Abreu Farias', 'douglas.farias@geradornv.com.br', '(66) 99376-8844', 'Engenharia', 11),
  ('Raiane da Cunha Avilla', 'raiane.avilla@geradornv.com.br', '(63) 99636-3487', 'Engenharia', 12),
  ('Icaro Garcia Maia', 'icaro.maia@geradornv.com.br', '(47) 98393-6325', 'Engenharia', 13),
  ('Juraci Barellos Reis', 'juraci.reis@geradornv.com.br', '(97) 99833-3342', 'Engenharia', 14),
  ('Messias Dutra Saraiva', 'messias.saraiva@geradornv.com.br', '(22) 99962-2241', 'Engenharia', 15),
  ('Pedro Guerini Fontes', 'pedro.fontes@geradornv.com.br', '(54) 97455-6104', 'Engenharia', 16),
  ('Edison Barboza Giacomini', 'edison.giacomini@geradornv.com.br', '(63) 97432-4608', 'Engenharia', 17),
  ('Arielle Claudino de Souza', 'arielle.souza@geradornv.com.br', '(65) 98716-8146', 'Engenharia', 18),
  ('Sinolina Arlia Pinto', 'sinolina.pinto@geradornv.com.br', '(68) 97341-3675', 'Engenharia', 19);

/**
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

INSERT INTO chamado_contato (id_chamado, id_contato) VALUES
(1, 1),
(1, 2),
(2, 2);

INSERT INTO chamado (id, dtcriacao, status,id_cliente) VALUES
(4, '2023-09-01 11:18:29', 'FILA', 3),
(5, '2023-09-01 14:27:29', 'FILA', 4),
(6, '2023-09-04 12:36:29', 'FILA', 5),
(7, '2023-09-05 17:45:29', 'FILA', 6),
(8, '2023-09-05 15:54:29', 'FILA', 7),
(9, '2023-09-06 12:13:29', 'FILA', 8),
(10, '2023-09-07 13:22:29', 'FILA', 9),
(11, '2023-09-08 16:31:29', 'FILA', 10),
(12, '2023-09-11 14:49:29', 'FILA', 11),
(13, '2023-09-12 13:58:29', 'FILA', 12),
(14, '2023-09-12 14:17:29', 'FILA', 13),
(15, '2023-09-13 13:26:29', 'FILA', 14),
(16, '2023-09-14 11:35:29', 'FILA', 15),
(17, '2023-09-15 13:45:29', 'FILA', 16),
(18, '2023-09-15 13:54:29', 'FILA', 17),
(19, '2023-09-18 15:23:29', 'FILA', 18),
(20, '2023-09-19 14:12:29', 'FILA', 19),
(21, '2023-09-19 13:41:29', 'FILA', 20);


INSERT INTO ocorrencia_ct (serial, id_produto, id_chamado, descricao) VALUES
('35810742459021', 29, 4,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('44554443583669', 75, 5,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('58533909538718', 43, 6,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('35051414240058', 63, 7,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('35810742459021', 29, 8,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('44554443583669', 75, 9,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('58533909538718', 43, 10,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('35051414240058', 63, 11,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('35810742459021', 29, 12,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('44554443583669', 75, 13,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('58533909538718', 43, 14,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('35051414240058', 63, 15,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('35810742459021', 29, 16,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('44554443583669', 75, 17,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('58533909538718', 43, 18,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('35051414240058', 63, 19,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('35810742459021', 29, 20,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa'),
('44554443583669', 75, 21,'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui sed quia consequuntur magni dolores eos qui sed quia consequunaa');



INSERT INTO status
(id, data, status_type, usuario, status, id_chamado, id_item_chamado) VALUES
(8,'2023-09-01 11:18:29','ITEM','Administrador','PENDENTE',NULL,5),
(9,'2023-09-01 11:18:29','CHAMADO','Administrador','FILA',4,NULL),
(10,'2023-09-01 14:27:29','ITEM','Administrador','PENDENTE',NULL,6),
(11,'2023-09-01 14:27:29','CHAMADO','Administrador','FILA',5,NULL),
(12,'2023-09-04 12:36:29','ITEM','Administrador','PENDENTE',NULL,7),
(13,'2023-09-04 12:36:29','CHAMADO','Administrador','FILA',6,NULL),
(14,'2023-09-05 17:45:29','ITEM','Administrador','PENDENTE',NULL,8),
(15,'2023-09-05 17:45:29','CHAMADO','Administrador','FILA',7,NULL),
(16,'2023-09-05 15:54:29','ITEM','Administrador','PENDENTE',NULL,9),
(17,'2023-09-05 15:54:29','CHAMADO','Administrador','FILA',8,NULL),
(18,'2023-09-06 12:13:29','ITEM','Administrador','PENDENTE',NULL,10),
(19,'2023-09-06 12:13:29','CHAMADO','Administrador','FILA',9,NULL),
(20,'2023-09-07 13:22:29','ITEM','Administrador','PENDENTE',NULL,11),
(21,'2023-09-07 13:22:29','CHAMADO','Administrador','FILA',10,NULL),
(22,'2023-09-08 16:31:29','ITEM','Administrador','PENDENTE',NULL,12),
(23,'2023-09-08 16:31:29','CHAMADO','Administrador','FILA',11,NULL),
(24,'2023-09-11 14:49:29','ITEM','Administrador','PENDENTE',NULL,13),
(25,'2023-09-11 14:49:29','CHAMADO','Administrador','FILA',12,NULL),
(26,'2023-09-12 13:58:29','ITEM','Administrador','PENDENTE',NULL,14),
(27,'2023-09-12 13:58:29','CHAMADO','Administrador','FILA',13,NULL),
(28,'2023-09-12 14:17:29','ITEM','Administrador','PENDENTE',NULL,15),
(29,'2023-09-12 14:17:29','CHAMADO','Administrador','FILA',14,NULL),
(30,'2023-09-13 13:26:29','ITEM','Administrador','PENDENTE',NULL,16),
(31,'2023-09-13 13:26:29','CHAMADO','Administrador','FILA',15,NULL),
(32,'2023-09-14 11:35:29','ITEM','Administrador','PENDENTE',NULL,17),
(33,'2023-09-14 11:35:29','CHAMADO','Administrador','FILA',16,NULL),
(34,'2023-09-15 13:45:29','ITEM','Administrador','PENDENTE',NULL,18),
(35,'2023-09-15 13:45:29','CHAMADO','Administrador','FILA',17,NULL),
(36,'2023-09-15 13:54:29','ITEM','Administrador','PENDENTE',NULL,19),
(37,'2023-09-15 13:54:29','CHAMADO','Administrador','FILA',18,NULL),
(38,'2023-09-18 15:23:29','ITEM','Administrador','PENDENTE',NULL,20),
(39,'2023-09-18 15:23:29','CHAMADO','Administrador','FILA',19,NULL),
(40,'2023-09-19 14:12:29','ITEM','Administrador','PENDENTE',NULL,21),
(41,'2023-09-19 14:12:29','CHAMADO','Administrador','FILA',20,NULL),
(42,'2023-09-19 13:41:29','ITEM','Administrador','PENDENTE',NULL,22),
(43,'2023-09-19 13:41:29','CHAMADO','Administrador','FILA',21,NULL);

INSERT INTO chamado_contato (id_chamado, id_contato) VALUES
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11),
(12, 12),
(13, 13),
(14, 14),
(15, 15),
(16, 16),
(17, 17),
(18, 18),
(19, 19),
(20, 20),
(21, 20);
**/