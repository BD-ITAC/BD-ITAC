CREATE TABLE crisis (
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(30) NOT NULL,
telefone VARCHAR(30) NOT NULL,
email VARCHAR(50),
descricao VARCHAR(50),
categoria INT NOT NULL ,
latitude BIGINT,
longitude BIGINT,
fotografia text);


CREATE TABLE usuario (
usu_id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
usu_nome VARCHAR(30) NOT NULL,
usu_telefone VARCHAR(30) NOT NULL,
usu_email VARCHAR(50),
usu_password VARCHAR(50));

INSERT INTO `bditac`.`usuario`
(
`usu_nome`,
`usu_telefone`,
`usu_email`,
`usu_password`)
VALUES
('Teste',
'99999-9999',
'teste@teste.com',
'teste');
