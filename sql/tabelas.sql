CREATE TABLE bditac.crise (
cri_id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
cri_nome VARCHAR(30) NOT NULL,
cri_telefone VARCHAR(30) NOT NULL,
cri_email VARCHAR(50),
cri_descricao VARCHAR(50),
cri_categoria INT NOT NULL ,
cri_latitude BIGINT,
cri_longitude BIGINT,
cri_dh_inicio TIMESTAMP,
cri_dh_fim TIMESTAMP,
cri_ativo BOOLEAN,
cri_fotografia text);


CREATE TABLE bditac.usuario (
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
INSERT INTO `bditac`.`usuario`
(
`usu_nome`,
`usu_telefone`,
`usu_email`,
`usu_password`)
VALUES
('admin',
'99999-9999',
'adm@gmail.com',
'admin');
