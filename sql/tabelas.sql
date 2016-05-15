CREATE TABLE crisis (
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(30) NOT NULL,
telefone VARCHAR(30) NOT NULL,
email VARCHAR(50),
descricao VARCHAR(50),
categoria INT NOT NULL ,
latitude BIGINT,
longitude BIGINT,
fotografia text)