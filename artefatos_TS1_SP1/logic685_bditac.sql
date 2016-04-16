/*
Navicat MySQL Data Transfer

Source Server         : BD-ITAC
Source Server Version : 50538
Source Host           : 179.190.55.130:3306
Source Database       : logic685_bditac

Target Server Type    : MYSQL
Target Server Version : 50538
File Encoding         : 65001

Date: 2016-04-15 23:26:09
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `abreviacao`
-- ----------------------------
DROP TABLE IF EXISTS `abreviacao`;
CREATE TABLE `abreviacao` (
  `abv_id` int(11) NOT NULL AUTO_INCREMENT,
  `abv_abreviacao` varchar(10) DEFAULT NULL,
  `pal_id` int(11) NOT NULL,
  PRIMARY KEY (`abv_id`,`pal_id`),
  KEY `fk_abreviacao_palavra1_idx` (`pal_id`) USING BTREE,
  CONSTRAINT `abreviacao_ibfk_1` FOREIGN KEY (`pal_id`) REFERENCES `palavra` (`pal_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of abreviacao
-- ----------------------------

-- ----------------------------
-- Table structure for `api`
-- ----------------------------
DROP TABLE IF EXISTS `api`;
CREATE TABLE `api` (
  `api_id` int(11) NOT NULL AUTO_INCREMENT,
  `api_nome` varchar(45) NOT NULL,
  `api_ativa` bit(1) DEFAULT NULL,
  `api_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of api
-- ----------------------------

-- ----------------------------
-- Table structure for `crise`
-- ----------------------------
DROP TABLE IF EXISTS `crise`;
CREATE TABLE `crise` (
  `cri_id` int(11) NOT NULL AUTO_INCREMENT,
  `cri_descricao` varchar(60) NOT NULL,
  `cri_inicio` date DEFAULT NULL,
  `cri_final` date DEFAULT NULL,
  `cri_ativa` bit(1) DEFAULT NULL,
  `cri_regiao` multipolygon DEFAULT NULL,
  `cri_crt_id` int(11) NOT NULL,
  PRIMARY KEY (`cri_id`,`cri_crt_id`),
  KEY `fk_crise_crise_tipo1_idx` (`cri_crt_id`) USING BTREE,
  CONSTRAINT `crise_ibfk_1` FOREIGN KEY (`cri_crt_id`) REFERENCES `crise_tipo` (`crt_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crise
-- ----------------------------

-- ----------------------------
-- Table structure for `crise_abreviacao`
-- ----------------------------
DROP TABLE IF EXISTS `crise_abreviacao`;
CREATE TABLE `crise_abreviacao` (
  `cia_cri_id` int(11) NOT NULL,
  `cia_abv_id` int(11) NOT NULL,
  PRIMARY KEY (`cia_cri_id`,`cia_abv_id`),
  KEY `fk_crise_has_abreviacao_abreviacao1_idx` (`cia_abv_id`) USING BTREE,
  KEY `fk_crise_has_abreviacao_crise1_idx` (`cia_cri_id`) USING BTREE,
  CONSTRAINT `crise_abreviacao_ibfk_1` FOREIGN KEY (`cia_abv_id`) REFERENCES `abreviacao` (`abv_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `crise_abreviacao_ibfk_2` FOREIGN KEY (`cia_cri_id`) REFERENCES `crise` (`cri_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crise_abreviacao
-- ----------------------------

-- ----------------------------
-- Table structure for `crise_api`
-- ----------------------------
DROP TABLE IF EXISTS `crise_api`;
CREATE TABLE `crise_api` (
  `cap_cri_id` int(11) NOT NULL,
  `cap_api_id` int(11) NOT NULL,
  `cap_inicio` date DEFAULT NULL,
  `cap_final` date DEFAULT NULL,
  `cap_ativa` bit(1) DEFAULT NULL,
  `cap_key` varchar(50) DEFAULT NULL,
  `cap_secret` varchar(50) DEFAULT NULL,
  `cap_token` varchar(100) DEFAULT NULL,
  `cap_token_secret` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cap_cri_id`,`cap_api_id`),
  KEY `fk_crise_has_api_api1_idx` (`cap_api_id`) USING BTREE,
  KEY `fk_crise_has_api_crise1_idx` (`cap_cri_id`) USING BTREE,
  CONSTRAINT `crise_api_ibfk_1` FOREIGN KEY (`cap_api_id`) REFERENCES `api` (`api_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `crise_api_ibfk_2` FOREIGN KEY (`cap_cri_id`) REFERENCES `crise` (`cri_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crise_api
-- ----------------------------

-- ----------------------------
-- Table structure for `crise_has_crise_api`
-- ----------------------------
DROP TABLE IF EXISTS `crise_has_crise_api`;
CREATE TABLE `crise_has_crise_api` (
  `crise_cri_id` int(11) NOT NULL,
  `crise_api_cap_id` int(11) NOT NULL,
  PRIMARY KEY (`crise_cri_id`,`crise_api_cap_id`),
  KEY `fk_crise_has_crise_api_crise_api1_idx` (`crise_api_cap_id`),
  KEY `fk_crise_has_crise_api_crise_idx` (`crise_cri_id`),
  CONSTRAINT `fk_crise_has_crise_api_crise` FOREIGN KEY (`crise_cri_id`) REFERENCES `bditac`.`crise` (`cri_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_crise_has_crise_api_crise_api1` FOREIGN KEY (`crise_api_cap_id`) REFERENCES `bditac`.`crise_api` (`cap_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of crise_has_crise_api
-- ----------------------------

-- ----------------------------
-- Table structure for `crise_identificacao`
-- ----------------------------
DROP TABLE IF EXISTS `crise_identificacao`;
CREATE TABLE `crise_identificacao` (
  `cii_cri_id` int(11) NOT NULL,
  `cii_pal_id` int(11) NOT NULL,
  `cii_sentimento` char(1) DEFAULT NULL COMMENT 'P - Positivo\nN - Negativo\nO - Não se aplica',
  PRIMARY KEY (`cii_cri_id`,`cii_pal_id`),
  KEY `fk_crise_has_palavra_palavra1_idx` (`cii_pal_id`) USING BTREE,
  KEY `fk_crise_has_palavra_crise1_idx` (`cii_cri_id`) USING BTREE,
  CONSTRAINT `crise_identificacao_ibfk_1` FOREIGN KEY (`cii_cri_id`) REFERENCES `crise` (`cri_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `crise_identificacao_ibfk_2` FOREIGN KEY (`cii_pal_id`) REFERENCES `palavra` (`pal_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crise_identificacao
-- ----------------------------

-- ----------------------------
-- Table structure for `crise_sentimento`
-- ----------------------------
DROP TABLE IF EXISTS `crise_sentimento`;
CREATE TABLE `crise_sentimento` (
  `cis_cri_id` int(11) NOT NULL,
  `cis_pal_id` int(11) NOT NULL,
  `cis_sentimento` char(1) DEFAULT NULL COMMENT 'P - Positivo\nN - Negativo',
  PRIMARY KEY (`cis_cri_id`,`cis_pal_id`),
  KEY `fk_crise_has_palavra_palavra3_idx` (`cis_pal_id`) USING BTREE,
  KEY `fk_crise_has_palavra_crise3_idx` (`cis_cri_id`) USING BTREE,
  CONSTRAINT `crise_sentimento_ibfk_1` FOREIGN KEY (`cis_cri_id`) REFERENCES `crise` (`cri_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `crise_sentimento_ibfk_2` FOREIGN KEY (`cis_pal_id`) REFERENCES `palavra` (`pal_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crise_sentimento
-- ----------------------------

-- ----------------------------
-- Table structure for `crise_tempo`
-- ----------------------------
DROP TABLE IF EXISTS `crise_tempo`;
CREATE TABLE `crise_tempo` (
  `cit_cri_id` int(11) NOT NULL,
  `cit_pal_id` int(11) NOT NULL,
  PRIMARY KEY (`cit_cri_id`,`cit_pal_id`),
  KEY `fk_crise_has_palavra_palavra2_idx` (`cit_pal_id`) USING BTREE,
  KEY `fk_crise_has_palavra_crise2_idx` (`cit_cri_id`) USING BTREE,
  CONSTRAINT `crise_tempo_ibfk_1` FOREIGN KEY (`cit_cri_id`) REFERENCES `crise` (`cri_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `crise_tempo_ibfk_2` FOREIGN KEY (`cit_pal_id`) REFERENCES `palavra` (`pal_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crise_tempo
-- ----------------------------

-- ----------------------------
-- Table structure for `crise_tipo`
-- ----------------------------
DROP TABLE IF EXISTS `crise_tipo`;
CREATE TABLE `crise_tipo` (
  `crt_id` int(11) NOT NULL AUTO_INCREMENT,
  `crt_ctp_id` int(3) NOT NULL,
  `crt_ctt_id` int(3) NOT NULL,
  `crt_cts_id` int(3) NOT NULL,
  `crt_ctg_id` int(3) NOT NULL,
  `crt_ctc_id` int(3) NOT NULL,
  `crt_descricao` varchar(60) DEFAULT NULL,
  `crt_definicao` longtext,
  `crt_cobrade` char(9) DEFAULT NULL,
  PRIMARY KEY (`crt_id`,`crt_ctt_id`,`crt_ctg_id`,`crt_ctc_id`),
  KEY `fk_crise_tipo_crise_tipo_subtipo1_idx` (`crt_ctp_id`,`crt_ctt_id`,`crt_cts_id`,`crt_ctg_id`,`crt_ctc_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=261 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crise_tipo
-- ----------------------------
INSERT INTO `crise_tipo` VALUES ('196', '0', '1', '1', '1', '1', null, 'Vibrações do terreno que provocam oscilações verticais e horizontais na superfície da Terra (ondas sísmicas). Pode ser natural (tectônica) ou induzido (explosões, injeção profunda de líquidos e gás, extração de fluidos, alívio de carga de minas, enchimento de lagos artificiais).', '1.1.1.1.0');
INSERT INTO `crise_tipo` VALUES ('197', '0', '2', '1', '1', '1', null, 'Série de ondas geradas por deslocamento de um grande volume de água causado geralmente por terremotos, erupções vulcânicas ou movimentos de massa.', '1.1.1.2.0');
INSERT INTO `crise_tipo` VALUES ('198', '0', '0', '2', '1', '1', null, 'Produtos/materiais  vulcânicos  lançados  na atmosfera a partir de erupções vulcânicas.', '1.1.2.0.0');
INSERT INTO `crise_tipo` VALUES ('199', '1', '1', '3', '1', '1', null, 'As quedas de blocos são movimentos rápidos e acontecem quando materiais rochosos diversos e de volumes variáveis se destacam de encostas muito íngremes, num movimento tipo queda livre.\n\nOs tombamentos de blocos são movimentos de massa em que ocorre rotação de um bloco de solo ou rocha em torno de um ponto ou abaixo do centro de gravidade da massa desprendida.\n\nRolamentos de blocos são movimentos de blocos rochosos ao longo de encostas, que ocorrem  geralmente  pela perda de apoio (descalçamento).', '1.1.3.1.1');
INSERT INTO `crise_tipo` VALUES ('200', '2', '1', '3', '1', '1', null, 'As quedas de lascas são movimentos rápidos e acontecem quando fatias delgadas formadas pelos fragmentos de rochas se destacam de encostas muito íngremes, num movimento tipo queda livre.', '1.1.3.1.2');
INSERT INTO `crise_tipo` VALUES ('201', '3', '1', '3', '1', '1', null, 'Os rolamentos de matacães são caracterizados por  movimentos  rápidos  e  acontecem quando materiais rochosos diversos e de volumes variáveis se destacam de encostas e movimentam-se num plano inclinado.', '1.1.3.1.3');
INSERT INTO `crise_tipo` VALUES ('202', '4', '1', '3', '1', '1', null, 'As quedas de lajes são movimentos rápidos e acontecem quando fragmentos de rochas extensas de superfície mais ou menos plana e de pouca espessura se destacam de encostas muito íngremes, num movimento tipo queda livre.', '1.1.3.1.4');
INSERT INTO `crise_tipo` VALUES ('203', '1', '2', '3', '1', '1', null, 'São movimentos rápidos de solo ou rocha, apresentando superfície de ruptura bem definida, de duração relativamente curta, de massas de terreno geralmente bem definidas quanto ao seu volume, cujo centro de gravidade se desloca para baixo e para fora do talude. Frequentemente, os primeiros sinais desses movimentos são a presença de fissuras.', '1.1.3.2.1');
INSERT INTO `crise_tipo` VALUES ('204', '1', '3', '3', '1', '1', null, 'Ocorrem quando, por índices pluviométricos excepcionais, o solo/lama, misturado com a água, tem comportamento de líquido viscoso, de extenso raio de ação e alto poder destrutivo.', '1.1.3.3.1');
INSERT INTO `crise_tipo` VALUES ('205', '2', '3', '3', '1', '1', null, 'Ocorrem quando, por índices pluviométricos excepcionais, rocha/detrito, misturado com a água, tem comportamento de líquido viscoso, de extenso raio de ação e alto poder destrutivo.', '1.1.3.3.2');
INSERT INTO `crise_tipo` VALUES ('206', '0', '4', '3', '1', '1', null, 'Afundamento rápido ou gradual do terreno devido ao colapso de cavidades, redução da porosidade do solo ou deformação de material argiloso.', '1.1.3.4.0');
INSERT INTO `crise_tipo` VALUES ('207', '0', '1', '4', '1', '1', null, 'Processo de desgaste (mecânico ou químico) que ocorre ao longo da linha da costa (rochosa ou praia) e se deve à ação das ondas, correntes marinhas e marés.', '1.1.4.1.0');
INSERT INTO `crise_tipo` VALUES ('208', '0', '2', '4', '1', '1', null, 'Desgaste das encostas dos rios que provoca desmoronamento de barrancos.', '1.1.4.2.0');
INSERT INTO `crise_tipo` VALUES ('209', '1', '3', '4', '1', '1', null, 'Remoção de uma camada delgada e uniforme do solo superficial provocada por fluxo hídrico não concentrado.', '1.1.4.3.1');
INSERT INTO `crise_tipo` VALUES ('210', '2', '3', '4', '1', '1', null, 'Evolução, em tamanho e profundidade, da desagregação e remoção das partículas do solo de sulcos provocada por escoamento hídrico superficial concentrado.', '1.1.4.3.2');
INSERT INTO `crise_tipo` VALUES ('211', '3', '3', '4', '1', '1', null, 'Evolução do processo de ravinamento, em tamanho e profundidade, em que a desagregação e remoção das partículas do solo são provocadas por escoamento hídrico superficial e subsuperficial (escoamento freático) concentrado.', '1.1.4.3.3');
INSERT INTO `crise_tipo` VALUES ('212', '0', '0', '1', '2', '1', null, 'Submersão  de  áreas  fora  dos  limites normais de um curso de água em zonas que normalmente  não  se  encontram  submersas. O transbordamento ocorre de modo gradual, geralmente ocasionado por chuvas prolongadas em áreas de planície.', '1.2.1.0.0');
INSERT INTO `crise_tipo` VALUES ('213', '0', '0', '2', '2', '1', null, 'Escoamento   superficial   de   alta   velocidade e  energia, provocado por  chuvas intensas e concentradas, normalmente em pequenas bacias de relevo acidentado. Caracterizada pela elevação súbita das vazões de determinada drenagem e transbordamento brusco da calha fluvial. Apresenta grande poder destrutivo.', '1.2.2.0.0');
INSERT INTO `crise_tipo` VALUES ('214', '0', '0', '3', '2', '1', null, 'Extrapolação da capacidade de escoamento de sistemas de drenagem urbana e consequente acúmulo de água em ruas, calçadas ou outras infraestruturas urbanas, em decorrência de precipitações intensas.', '1.2.3.0.0');
INSERT INTO `crise_tipo` VALUES ('215', '1', '1', '1', '3', '1', null, 'Intensificação dos ventos nas regiões litorâneas, movimentando dunas de areia sobre construções na orla.', '1.3.1.1.1');
INSERT INTO `crise_tipo` VALUES ('216', '2', '1', '1', '3', '1', null, 'São  ondas violentas que  geram  uma  maior agitação do mar próximo à praia. Ocorrem quando rajadas fortes de vento fazem subir o nível do oceano em mar aberto e essa intensificação das correntes marítimas carrega  uma  enorme  quantidade  de  água em direção ao litoral. Em consequência, as praias inundam, as ondas se tornam maiores e a orla pode ser devastada alagando ruas e destruindo edificações.', '1.3.1.1.2');
INSERT INTO `crise_tipo` VALUES ('217', '0', '2', '1', '3', '1', null, 'Frente fria é uma massa de ar frio que avança sobre uma região, provocando queda brusca da temperatura local, com período de duração inferior à friagem.\n\nZona de convergência é uma região que está ligada à tempestade causada por uma zona de baixa pressão atmosférica, provocando forte deslocamento de massas de ar, vendavais, chuvas intensas e até queda de granizo.', '1.3.1.2.0');
INSERT INTO `crise_tipo` VALUES ('218', '1', '1', '2', '3', '1', null, 'Coluna de ar que gira de forma violenta e muito  perigosa,  estando  em  contato  com a terra e a base de uma nuvem de grande desenvolvimento vertical. Essa coluna de ar pode percorrer vários quilômetros e deixa um rastro de destruição pelo caminho percorrido.', '1.3.2.1.1');
INSERT INTO `crise_tipo` VALUES ('219', '2', '1', '2', '3', '1', null, 'Tempestade  com  intensa  atividade  elétrica no interior das nuvens, com grande desenvolvimento vertical.', '1.3.2.1.2');
INSERT INTO `crise_tipo` VALUES ('220', '3', '1', '2', '3', '1', null, 'Precipitação de pedaços irregulares de gelo.', '1.3.2.1.3');
INSERT INTO `crise_tipo` VALUES ('221', '4', '1', '2', '3', '1', null, 'São chuvas que ocorrem com acumulados significativos, causando múltiplos desastres (ex.: inundações, movimentos de massa, enxurradas, etc.).', '1.3.2.1.4');
INSERT INTO `crise_tipo` VALUES ('222', '5', '1', '2', '3', '1', null, 'Forte deslocamento de uma massa de ar em uma região.', '1.3.2.1.5');
INSERT INTO `crise_tipo` VALUES ('223', '0', '1', '3', '3', '1', null, 'É um período prolongado de tempo excessivamente quente e desconfortável, onde as temperaturas ficam acima de um valor normal esperado para aquela região em determinado período do ano. Geralmente é adotado um período mínimo de três dias com temperaturas\n5°C acima dos valores máximos médios.', '1.3.3.1.0');
INSERT INTO `crise_tipo` VALUES ('224', '1', '2', '3', '3', '1', null, 'Período  de  tempo  que  dura,  no  mínimo, de três a quatro dias, e os valores de temperatura mínima do ar ficam abaixo dos valores esperados para determinada região em um período do ano.', '1.3.3.2.1');
INSERT INTO `crise_tipo` VALUES ('225', '2', '2', '3', '3', '1', null, 'Formação de uma camada de cristais de gelo na superfície ou na folhagem exposta.', '1.3.3.2.2');
INSERT INTO `crise_tipo` VALUES ('226', '0', '1', '1', '4', '1', null, 'Período prolongado de baixa ou nenhuma pluviosidade, em que a perda de umidade do solo é superior à sua reposição.', '1.4.1.1.0');
INSERT INTO `crise_tipo` VALUES ('227', '0', '2', '1', '4', '1', null, 'A seca é uma estiagem prolongada, durante o período de tempo suficiente para que a falta de precipitação provoque grave desequilíbrio hidrológico.', '1.4.1.2.0');
INSERT INTO `crise_tipo` VALUES ('228', '1', '3', '1', '4', '1', null, 'Propagação de fogo sem controle, em qualquer tipo de vegetação situada em áreas legalmente protegidas.', '1.4.1.3.1');
INSERT INTO `crise_tipo` VALUES ('229', '2', '3', '1', '4', '1', null, 'Propagação de fogo sem controle, em qualquer tipo de vegetação que não se encontre em áreas sob proteção legal, acarretando queda da qualidade do ar.', '1.4.1.3.2');
INSERT INTO `crise_tipo` VALUES ('230', '0', '4', '1', '4', '1', null, 'Queda da taxa de vapor de água suspensa na atmosfera para níveis abaixo de 20%.', '1.4.1.4.0');
INSERT INTO `crise_tipo` VALUES ('231', '0', '1', '1', '5', '1', null, 'Aumento  brusco,  significativo  e transitório da ocorrência de doenças infecciosas geradas por vírus.', '1.5.1.1.0');
INSERT INTO `crise_tipo` VALUES ('232', '0', '2', '1', '5', '1', null, 'Aumento brusco, significativo e transitório da ocorrência de doenças infecciosas geradas por bactérias.', '1.5.1.2.0');
INSERT INTO `crise_tipo` VALUES ('233', '0', '3', '1', '5', '1', null, 'Aumento brusco, significativo e transitório da ocorrência de doenças infecciosas geradas por parasitas.', '1.5.1.3.0');
INSERT INTO `crise_tipo` VALUES ('234', '0', '4', '1', '5', '1', null, 'Aumento brusco, significativo e transitório da ocorrência de doenças infecciosas geradas por fungos.', '1.5.1.4.0');
INSERT INTO `crise_tipo` VALUES ('235', '0', '1', '2', '5', '1', null, 'Infestações   por  animais  que  alterem o equilíbrio ecológico de uma região, bacia hidrográfica ou bioma afetado por suas ações predatórias.', '1.5.2.1.0');
INSERT INTO `crise_tipo` VALUES ('236', '1', '2', '2', '5', '1', null, 'Aglomeração de microalgas em água doce ou em água salgada suficiente para causar alterações físicas, químicas ou biológicas em     sua     composição,     caracterizada por uma mudança de cor, tornando-se amarela, laranja, vermelha ou marrom.', '1.5.2.2.1');
INSERT INTO `crise_tipo` VALUES ('237', '2', '2', '2', '5', '1', null, 'Aglomeração de cianobactérias em reservatórios   receptores   de   descargas de dejetos domésticos, industriais e/ou agrícolas, provocando alterações das propriedades físicas, químicas ou biológicas da água.', '1.5.2.2.2');
INSERT INTO `crise_tipo` VALUES ('238', '0', '3', '2', '5', '1', null, 'Infestações que alterem o equilíbrio ecológico de uma região, bacia hidrográfica ou bioma afetado por suas ações predatórias.', '1.5.2.3.0');
INSERT INTO `crise_tipo` VALUES ('239', '0', '1', '1', '1', '2', null, 'Queda   de   satélites   que   possuem,   na sua composição, motores ou corpos radioativos, podendo ocasionar a liberação deste material.', '2.1.1.1.0');
INSERT INTO `crise_tipo` VALUES ('240', '0', '1', '2', '1', '2', null, 'Escapamento    acidental    de    radiação que excede os níveis de segurança estabelecidos na norma NN 3.01/006:2011 da CNEN.', '2.1.2.1.0');
INSERT INTO `crise_tipo` VALUES ('241', '0', '1', '3', '1', '2', null, 'Escapamento acidental ou não acidental de radiação originária de fontes radioativas diversas e que excede os níveis    de    segurança    estabelecidos na   norma   NN   3.01/006:2011   e   NN\n3.01/011:2011 da CNEN.', '2.1.3.1.0');
INSERT INTO `crise_tipo` VALUES ('242', '0', '1', '1', '2', '2', null, 'Liberação de produtos químicos diversos para o ambiente, provocada por explosão/ incêndio em plantas industriais ou outros sítios.', '2.2.1.1.0');
INSERT INTO `crise_tipo` VALUES ('243', '0', '1', '2', '2', '2', null, 'Derramamento de produtos químicos diversos em um sistema de abastecimento de água potável, que pode causar alterações nas qualidades físicas, químicas, biológicas.', '2.2.2.1.0');
INSERT INTO `crise_tipo` VALUES ('244', '0', '2', '2', '2', '2', null, 'Derramamento de produtos químicos diversos em lagos, rios, mar e reservatórios subterrâneos de água, que pode causar alterações nas qualidades físicas, químicas e biológicas.', '2.2.2.2.0');
INSERT INTO `crise_tipo` VALUES ('245', '0', '1', '3', '2', '2', null, 'Agente de natureza nuclear ou radiológica, química   ou   biológica,   considerado como  perigoso,  e que  pode  ser  utilizado intencionalmente por terroristas ou grupamentos militares em atentados ou em caso de guerra.', '2.2.3.1.0');
INSERT INTO `crise_tipo` VALUES ('246', '0', '1', '4', '2', '2', null, 'Extravasamento    de    produtos    perigosos transportados no modal rodoviário.', '2.2.4.1.0');
INSERT INTO `crise_tipo` VALUES ('247', '0', '2', '4', '2', '2', null, 'Extravasamento    de    produtos    perigosos transportados no modal ferroviário.', '2.2.4.2.0');
INSERT INTO `crise_tipo` VALUES ('248', '0', '3', '4', '2', '2', null, 'Extravasamento    de    produtos    perigosos transportados no modal aéreo.', '2.2.4.3.0');
INSERT INTO `crise_tipo` VALUES ('249', '0', '4', '4', '2', '2', null, 'Extravasamento    de    produtos    perigosos transportados no modal dutoviário.', '2.2.4.4.0');
INSERT INTO `crise_tipo` VALUES ('250', '0', '5', '4', '2', '2', null, 'Extravasamento    de    produtos    perigosos transportados no modal marítimo.', '2.2.4.5.0');
INSERT INTO `crise_tipo` VALUES ('251', '0', '6', '4', '2', '2', null, 'Extravasamento    de    produtos    perigosos transportados no modal aquaviário.', '2.2.4.6.0');
INSERT INTO `crise_tipo` VALUES ('252', '0', '1', '1', '3', '2', null, 'Propagação descontrolada do fogo em plantas e distritos industriais, parques e depósitos.', '2.3.1.1.0');
INSERT INTO `crise_tipo` VALUES ('253', '0', '2', '1', '3', '2', null, 'Propagação   descontrolada   do   fogo   em conjuntos habitacionais de grande densidade.', '2.3.1.2.0');
INSERT INTO `crise_tipo` VALUES ('254', '0', '0', '1', '4', '2', null, 'Queda de estrutura civil.', '2.4.1.0.0');
INSERT INTO `crise_tipo` VALUES ('255', '0', '0', '2', '4', '2', null, 'Rompimento ou colapso de barragens.', '2.4.2.0.0');
INSERT INTO `crise_tipo` VALUES ('256', '0', '0', '1', '5', '2', null, 'Acidente  no  modal  rodoviário  envolvendo o transporte de passageiros ou cargas não perigosas.', '2.5.1.0.0');
INSERT INTO `crise_tipo` VALUES ('257', '0', '0', '2', '5', '2', null, 'Acidente com a participação direta de veículo ferroviário de transporte de passageiros ou cargas não perigosas.', '2.5.2.0.0');
INSERT INTO `crise_tipo` VALUES ('258', '0', '0', '3', '5', '2', null, 'Acidente no modal aéreo envolvendo o transporte de passageiros ou cargas não perigosas.', '2.5.3.0.0');
INSERT INTO `crise_tipo` VALUES ('259', '0', '0', '4', '5', '2', null, 'Acidente com embarcações marítimas destinadas ao transporte de passageiros e cargas não perigosas.', '2.5.4.0.0');
INSERT INTO `crise_tipo` VALUES ('260', '0', '0', '5', '5', '2', null, 'Acidente com embarcações destinadas ao transporte de passageiros e cargas não perigosas.', '2.5.5.0.0');

-- ----------------------------
-- Table structure for `crise_tipo_classe`
-- ----------------------------
DROP TABLE IF EXISTS `crise_tipo_classe`;
CREATE TABLE `crise_tipo_classe` (
  `ctc_id` int(3) NOT NULL,
  `ctc_descricao` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ctc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crise_tipo_classe
-- ----------------------------
INSERT INTO `crise_tipo_classe` VALUES ('1', 'NATURAIS');
INSERT INTO `crise_tipo_classe` VALUES ('2', 'TECNOLÓGICOS');

-- ----------------------------
-- Table structure for `crise_tipo_grupo`
-- ----------------------------
DROP TABLE IF EXISTS `crise_tipo_grupo`;
CREATE TABLE `crise_tipo_grupo` (
  `ctg_id` int(3) NOT NULL,
  `ctg_ctc_id` int(3) NOT NULL,
  `ctg_descricao` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`ctg_id`,`ctg_ctc_id`),
  KEY `fk_crise_tipo_grupo_crise_tipo_classe1_idx` (`ctg_ctc_id`) USING BTREE,
  CONSTRAINT `crise_tipo_grupo_ibfk_1` FOREIGN KEY (`ctg_ctc_id`) REFERENCES `crise_tipo_classe` (`ctc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crise_tipo_grupo
-- ----------------------------
INSERT INTO `crise_tipo_grupo` VALUES ('1', '1', 'Geológico');
INSERT INTO `crise_tipo_grupo` VALUES ('1', '2', 'Desastres relacionados a substâncias radioativas');
INSERT INTO `crise_tipo_grupo` VALUES ('2', '1', 'Hidrológico');
INSERT INTO `crise_tipo_grupo` VALUES ('2', '2', 'Desastres relacionados a produtos perigosos');
INSERT INTO `crise_tipo_grupo` VALUES ('3', '1', 'Meteorológico');
INSERT INTO `crise_tipo_grupo` VALUES ('3', '2', 'Desastres relacionados a incêndios urbanos');
INSERT INTO `crise_tipo_grupo` VALUES ('4', '1', 'Climatológico');
INSERT INTO `crise_tipo_grupo` VALUES ('4', '2', 'Desastres relacionados a obras civis');
INSERT INTO `crise_tipo_grupo` VALUES ('5', '1', 'Biológico');
INSERT INTO `crise_tipo_grupo` VALUES ('5', '2', 'Desastres relacionados a transporte de passageiros e cargas não perigosas');

-- ----------------------------
-- Table structure for `crise_tipo_subgrupo`
-- ----------------------------
DROP TABLE IF EXISTS `crise_tipo_subgrupo`;
CREATE TABLE `crise_tipo_subgrupo` (
  `cts_id` int(3) NOT NULL,
  `cts_ctg_id` int(3) NOT NULL,
  `cts_ctc_id` int(3) NOT NULL,
  `cts_descricao` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`cts_id`,`cts_ctg_id`,`cts_ctc_id`),
  KEY `fk_crise_tipo_subgrupo_crise_tipo_grupo1_idx` (`cts_ctg_id`,`cts_ctc_id`) USING BTREE,
  CONSTRAINT `crise_tipo_subgrupo_ibfk_1` FOREIGN KEY (`cts_ctg_id`, `cts_ctc_id`) REFERENCES `crise_tipo_grupo` (`ctg_id`, `ctg_ctc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crise_tipo_subgrupo
-- ----------------------------
INSERT INTO `crise_tipo_subgrupo` VALUES ('1', '1', '1', 'Terremoto');
INSERT INTO `crise_tipo_subgrupo` VALUES ('1', '1', '2', 'Desastres siderais com riscos radioativos');
INSERT INTO `crise_tipo_subgrupo` VALUES ('1', '2', '1', 'Inundações');
INSERT INTO `crise_tipo_subgrupo` VALUES ('1', '2', '2', 'Desastres em plantas e distritos industriais, parques e armazenamentos com extravasamento de produtos perigosos');
INSERT INTO `crise_tipo_subgrupo` VALUES ('1', '3', '1', 'Sistemas de grande escala/Escala regional');
INSERT INTO `crise_tipo_subgrupo` VALUES ('1', '3', '2', 'Incêndios urbanos');
INSERT INTO `crise_tipo_subgrupo` VALUES ('1', '4', '1', 'Seca');
INSERT INTO `crise_tipo_subgrupo` VALUES ('1', '4', '2', 'Colapso de\nedificações');
INSERT INTO `crise_tipo_subgrupo` VALUES ('1', '5', '1', 'Epidemias');
INSERT INTO `crise_tipo_subgrupo` VALUES ('1', '5', '2', 'Transporte rodoviário');
INSERT INTO `crise_tipo_subgrupo` VALUES ('2', '1', '1', 'Emanação vulcânica');
INSERT INTO `crise_tipo_subgrupo` VALUES ('2', '1', '2', 'Desastres com substâncias e equipamentos radioativos de uso em pesquisas, indústrias e usinas nucleares');
INSERT INTO `crise_tipo_subgrupo` VALUES ('2', '2', '1', 'Enxurradas');
INSERT INTO `crise_tipo_subgrupo` VALUES ('2', '2', '2', 'Desastres relacionados à contaminação da água');
INSERT INTO `crise_tipo_subgrupo` VALUES ('2', '3', '1', 'Tempestades');
INSERT INTO `crise_tipo_subgrupo` VALUES ('2', '4', '2', 'Rompimento/ colapso de barragens');
INSERT INTO `crise_tipo_subgrupo` VALUES ('2', '5', '1', 'Infestações/ Pragas');
INSERT INTO `crise_tipo_subgrupo` VALUES ('2', '5', '2', 'Transporte ferroviário');
INSERT INTO `crise_tipo_subgrupo` VALUES ('3', '1', '1', 'Movimento de massa');
INSERT INTO `crise_tipo_subgrupo` VALUES ('3', '1', '2', 'Desastres relacionados com riscos de intensa poluição ambiental provocada por resíduos radioativos');
INSERT INTO `crise_tipo_subgrupo` VALUES ('3', '2', '1', 'Alagamentos');
INSERT INTO `crise_tipo_subgrupo` VALUES ('3', '2', '2', 'Desastres relacionados a conflitos bélicos');
INSERT INTO `crise_tipo_subgrupo` VALUES ('3', '3', '1', 'Temperaturas extremas');
INSERT INTO `crise_tipo_subgrupo` VALUES ('3', '5', '2', 'Transporte aéreo');
INSERT INTO `crise_tipo_subgrupo` VALUES ('4', '1', '1', 'Erosão');
INSERT INTO `crise_tipo_subgrupo` VALUES ('4', '2', '2', 'Desastres relacionados a transporte de produtos perigosos');
INSERT INTO `crise_tipo_subgrupo` VALUES ('4', '5', '2', 'Transporte marítimo');
INSERT INTO `crise_tipo_subgrupo` VALUES ('5', '5', '2', 'Transporte aquaviário');

-- ----------------------------
-- Table structure for `crise_tipo_subtipo`
-- ----------------------------
DROP TABLE IF EXISTS `crise_tipo_subtipo`;
CREATE TABLE `crise_tipo_subtipo` (
  `ctp_id` int(3) NOT NULL,
  `ctp_ctt_id` int(3) NOT NULL,
  `ctp_cts_id` int(3) NOT NULL,
  `ctp_ctg_id` int(3) NOT NULL,
  `ctp_ctc_id` int(3) NOT NULL,
  `ctp_descricao` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`ctp_id`,`ctp_ctt_id`,`ctp_cts_id`,`ctp_ctg_id`,`ctp_ctc_id`),
  KEY `fk_crise_tipo_subtipo_crise_tipo_tipo1_idx` (`ctp_ctt_id`,`ctp_cts_id`,`ctp_ctg_id`,`ctp_ctc_id`) USING BTREE,
  CONSTRAINT `crise_tipo_subtipo_ibfk_1` FOREIGN KEY (`ctp_ctt_id`, `ctp_cts_id`, `ctp_ctg_id`, `ctp_ctc_id`) REFERENCES `crise_tipo_tipo` (`ctt_id`, `ctt_cts_id`, `ctt_ctg_id`, `ctt_ctc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crise_tipo_subtipo
-- ----------------------------
INSERT INTO `crise_tipo_subtipo` VALUES ('0', '0', '1', '1', '1', 'Nenhum');
INSERT INTO `crise_tipo_subtipo` VALUES ('1', '1', '1', '3', '1', 'Ventos costeiros (mobilidade de dunas)');
INSERT INTO `crise_tipo_subtipo` VALUES ('1', '1', '2', '3', '1', 'Tornados');
INSERT INTO `crise_tipo_subtipo` VALUES ('1', '1', '3', '1', '1', 'Blocos');
INSERT INTO `crise_tipo_subtipo` VALUES ('1', '2', '2', '5', '1', 'Marés vermelhas');
INSERT INTO `crise_tipo_subtipo` VALUES ('1', '2', '3', '1', '1', 'Deslizamentos de solo e/ou rocha');
INSERT INTO `crise_tipo_subtipo` VALUES ('1', '2', '3', '3', '1', 'Friagem');
INSERT INTO `crise_tipo_subtipo` VALUES ('1', '3', '1', '4', '1', 'Incêndios em parques, áreas de proteção ambiental e áreas de preservação permanente nacionais, estaduais ou municipais');
INSERT INTO `crise_tipo_subtipo` VALUES ('1', '3', '3', '1', '1', 'Solo/Lama');
INSERT INTO `crise_tipo_subtipo` VALUES ('1', '3', '4', '1', '1', 'Laminar');
INSERT INTO `crise_tipo_subtipo` VALUES ('2', '1', '1', '3', '1', 'Marés de tempestade (ressaca)');
INSERT INTO `crise_tipo_subtipo` VALUES ('2', '1', '2', '3', '1', 'Tempestade de raios');
INSERT INTO `crise_tipo_subtipo` VALUES ('2', '1', '3', '1', '1', 'Lascas');
INSERT INTO `crise_tipo_subtipo` VALUES ('2', '2', '2', '5', '1', 'Cianobactérias em reservatórios');
INSERT INTO `crise_tipo_subtipo` VALUES ('2', '2', '3', '3', '1', 'Geadas');
INSERT INTO `crise_tipo_subtipo` VALUES ('2', '3', '1', '4', '1', 'Incêndios em áreas não protegidas, com reflexos na qualidade do ar');
INSERT INTO `crise_tipo_subtipo` VALUES ('2', '3', '3', '1', '1', 'Rocha/ Detrito');
INSERT INTO `crise_tipo_subtipo` VALUES ('2', '3', '4', '1', '1', 'Ravinas');
INSERT INTO `crise_tipo_subtipo` VALUES ('3', '1', '2', '3', '1', 'Granizo');
INSERT INTO `crise_tipo_subtipo` VALUES ('3', '1', '3', '1', '1', 'Matacães');
INSERT INTO `crise_tipo_subtipo` VALUES ('3', '3', '4', '1', '1', 'Boçorocas');
INSERT INTO `crise_tipo_subtipo` VALUES ('4', '1', '2', '3', '1', 'Chuvas intensas');
INSERT INTO `crise_tipo_subtipo` VALUES ('4', '1', '3', '1', '1', 'Lajes');
INSERT INTO `crise_tipo_subtipo` VALUES ('5', '1', '2', '3', '1', 'Vendaval');

-- ----------------------------
-- Table structure for `crise_tipo_tipo`
-- ----------------------------
DROP TABLE IF EXISTS `crise_tipo_tipo`;
CREATE TABLE `crise_tipo_tipo` (
  `ctt_id` int(3) NOT NULL,
  `ctt_cts_id` int(3) NOT NULL,
  `ctt_ctg_id` int(3) NOT NULL,
  `ctt_ctc_id` int(3) NOT NULL,
  `ctt_descricao` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`ctt_id`,`ctt_cts_id`,`ctt_ctg_id`,`ctt_ctc_id`),
  KEY `fk_crise_tipo_tipo_crise_tipo_subgrupo1_idx` (`ctt_cts_id`,`ctt_ctg_id`,`ctt_ctc_id`) USING BTREE,
  CONSTRAINT `crise_tipo_tipo_ibfk_1` FOREIGN KEY (`ctt_cts_id`, `ctt_ctg_id`, `ctt_ctc_id`) REFERENCES `crise_tipo_subgrupo` (`cts_id`, `cts_ctg_id`, `cts_ctc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of crise_tipo_tipo
-- ----------------------------
INSERT INTO `crise_tipo_tipo` VALUES ('0', '1', '1', '1', 'Nenhum');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '1', '1', '1', 'Tremor de terra');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '1', '1', '2', 'Queda de satélite (radionuclídeos)');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '1', '2', '2', 'Liberação de produtos químicos para a atmosfera causada por explosão ou incêndio');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '1', '3', '1', 'Ciclones');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '1', '3', '2', 'Incêndios em plantas e distritos industriais, parques e depósitos');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '1', '4', '1', 'Estiagem');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '1', '5', '1', 'Doenças infecciosas virais');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '2', '1', '2', 'Fontes radioativas em processos de produção');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '2', '2', '2', 'Liberação de produtos químicos nos sistemas de água potável');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '2', '3', '1', 'Tempestade local/Convectiva');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '2', '5', '1', 'Infestações de animais');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '3', '1', '1', 'Quedas, tombamentos e rolamentos');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '3', '1', '2', 'Outras fontes de liberação de radionuclídeos para o meio ambiente');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '3', '2', '2', 'Liberação de produtos químicos e contaminação como consequência de ações militares');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '3', '3', '1', 'Onda de calor');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '4', '1', '1', 'Erosão costeira/Marinha');
INSERT INTO `crise_tipo_tipo` VALUES ('1', '4', '2', '2', 'Transporte rodoviário');
INSERT INTO `crise_tipo_tipo` VALUES ('2', '1', '1', '1', 'Tsunami');
INSERT INTO `crise_tipo_tipo` VALUES ('2', '1', '3', '1', 'Frentes frias/Zonas de convergência');
INSERT INTO `crise_tipo_tipo` VALUES ('2', '1', '3', '2', 'Incêndios em aglomerados residenciais');
INSERT INTO `crise_tipo_tipo` VALUES ('2', '1', '4', '1', 'Seca');
INSERT INTO `crise_tipo_tipo` VALUES ('2', '1', '5', '1', 'Doenças infecciosas bacterianas');
INSERT INTO `crise_tipo_tipo` VALUES ('2', '2', '2', '2', 'Derramamento de produtos químicos em ambiente lacustre, fluvial, marinho e aquífero');
INSERT INTO `crise_tipo_tipo` VALUES ('2', '2', '5', '1', 'Infestações de algas');
INSERT INTO `crise_tipo_tipo` VALUES ('2', '3', '1', '1', 'Deslizamentos');
INSERT INTO `crise_tipo_tipo` VALUES ('2', '3', '3', '1', 'Onda de frio');
INSERT INTO `crise_tipo_tipo` VALUES ('2', '4', '1', '1', 'Erosão de\nmargem fluvial');
INSERT INTO `crise_tipo_tipo` VALUES ('2', '4', '2', '2', 'Transporte ferroviário');
INSERT INTO `crise_tipo_tipo` VALUES ('3', '1', '4', '1', 'Incêndio\nflorestal');
INSERT INTO `crise_tipo_tipo` VALUES ('3', '1', '5', '1', 'Doenças infecciosas parasíticas');
INSERT INTO `crise_tipo_tipo` VALUES ('3', '2', '5', '1', 'Outras infestações');
INSERT INTO `crise_tipo_tipo` VALUES ('3', '3', '1', '1', 'Corridas de massa');
INSERT INTO `crise_tipo_tipo` VALUES ('3', '4', '1', '1', 'Erosão continental');
INSERT INTO `crise_tipo_tipo` VALUES ('3', '4', '2', '2', 'Transporte aéreo');
INSERT INTO `crise_tipo_tipo` VALUES ('4', '1', '4', '1', 'Baixa umidade do ar');
INSERT INTO `crise_tipo_tipo` VALUES ('4', '1', '5', '1', 'Doenças infecciosas fúngicas');
INSERT INTO `crise_tipo_tipo` VALUES ('4', '3', '1', '1', 'Subsidências e colapsos');
INSERT INTO `crise_tipo_tipo` VALUES ('4', '4', '2', '2', 'Transporte dutoviário');
INSERT INTO `crise_tipo_tipo` VALUES ('5', '4', '2', '2', 'Transporte marítimo');
INSERT INTO `crise_tipo_tipo` VALUES ('6', '4', '2', '2', 'Transporte aquaviário');

-- ----------------------------
-- Table structure for `palavra`
-- ----------------------------
DROP TABLE IF EXISTS `palavra`;
CREATE TABLE `palavra` (
  `pal_id` int(11) NOT NULL AUTO_INCREMENT,
  `pal_palavra` varchar(46) NOT NULL,
  `pal_tipo` char(1) DEFAULT NULL COMMENT 'S - Substantivo\nV - Verbo\nA - Adjetivo',
  `pal_feminino` int(11) DEFAULT NULL,
  `pal_diminutivo` int(11) DEFAULT NULL,
  `pal_aumentativo` int(11) DEFAULT NULL,
  `pal_plural` int(11) DEFAULT NULL,
  `pal_sentimento` char(1) DEFAULT NULL COMMENT 'P - Positivo\nN - Negativo',
  `pal_tempo` char(1) DEFAULT NULL COMMENT 'P - Passado\nR - Presente\nF - Futuro',
  `pal_infinitivo` int(11) DEFAULT NULL,
  PRIMARY KEY (`pal_id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of palavra
-- ----------------------------
INSERT INTO `palavra` VALUES ('1', 'água', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('2', 'rio', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('3', 'inundação', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('4', 'região', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('5', 'áreas', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('6', 'inundações', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('7', 'chuvas', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('8', 'chuva', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('9', 'águas', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('10', 'rios', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('11', 'enchentes', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('12', 'enchente', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('13', 'nível', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('14', 'vítimas', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('15', 'desabrigados', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('16', 'civil', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('17', 'defesa', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('18', 'risco', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('19', 'danos', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('20', 'desalojados', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('21', 'deslizamentos', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('22', 'alagamento', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('23', 'drenagem', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('24', 'tragédia', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('25', 'emergência', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('26', 'afetados', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('27', 'lama', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('28', 'canal', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('29', 'escoamento', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('30', 'cheias', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('31', 'precipitação', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('32', 'barragens', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('33', 'alerta', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('34', 'afetadas', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('35', 'deslizamento', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('36', 'transbordamento', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('37', 'intensas', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('38', 'alagamentos', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('39', 'desastres', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('40', 'enxurrada', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('41', 'cheia', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('42', 'regiões', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('43', 'temporal', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('44', 'lagos', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('45', 'diques', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('46', 'desastre', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('47', 'precipitações', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('48', 'córregos', null, null, null, null, null, null, null, null);
INSERT INTO `palavra` VALUES ('49', 'barragem', null, null, null, null, null, null, null, null);
