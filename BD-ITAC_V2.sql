DROP DATABASE IF EXISTS BDITAC;
CREATE DATABASE IF NOT EXISTS BDITAC;
USE BDITAC;

-- Criação da tabela "usuário"
CREATE TABLE usuario
(
	usu_id		INT			AUTO_INCREMENT		PRIMARY KEY,
	usu_nm		CHAR(50)	NOT NULL,
	usu_email	CHAR(100)	NOT NULL,
	usu_fone	CHAR(20)	NOT NULL
);

-- Criação da tabela "evento"
CREATE TABLE evento
(
	eve_id		INT			AUTO_INCREMENT		PRIMARY KEY,
	eve_ds		CHAR(100)	NOT NULL
);

-- Criação da tabela "classificação"
CREATE TABLE classificacao
(
	cla_id		INT			AUTO_INCREMENT		PRIMARY KEY,
	cla_ds		CHAR(100)	NOT NULL
);

-- Criação da tabela "status"
CREATE TABLE status
(
	sta_id		INT			AUTO_INCREMENT		PRIMARY KEY,
	sta_ds		CHAR(100)	NOT NULL
);

-- Criação da tabela "geografica"
CREATE TABLE geografica
(
	geo_id		INT			AUTO_INCREMENT		PRIMARY KEY,
	geo_cat		CHAR(50)	NOT NULL,
	geo_nm		CHAR(255)	NOT NULL,
	geo_uf		CHAR(100)	NOT NULL,
	geo_long	CHAR(255)	NOT NULL,
	geo_lat		CHAR(255)	NOT NULL,
	geo_alt		CHAR(255)	NOT NULL
);

-- Criação da tabela "ocorrência"
CREATE TABLE ocorrencia
(
	oco_id		INT				AUTO_INCREMENT		PRIMARY KEY,
	oco_nm		CHAR(100)		NOT NULL,
	oco_ds		VARCHAR(255),
    oco_geo_cod	INT,
	oco_usu_cod	INT,
	oco_eve_cod	INT,
	oco_cla_cod	INT,
	oco_sta_cod	INT,
	CONSTRAINT FK_oco_usu_cod FOREIGN KEY (oco_usu_cod)
		REFERENCES usuario (usu_id),
	CONSTRAINT FK_oco_eve_cod FOREIGN KEY (oco_eve_cod)
		REFERENCES evento (eve_id),
	CONSTRAINT FK_oco_cla_cod FOREIGN KEY (oco_cla_cod)
		REFERENCES classificacao (cla_id),
	CONSTRAINT FK_oco_sta_cod FOREIGN KEY (oco_sta_cod)
		REFERENCES status (sta_id),
	CONSTRAINT FK_oco_geo_cod FOREIGN KEY (oco_geo_cod)
		REFERENCES geografica (geo_id)
);
