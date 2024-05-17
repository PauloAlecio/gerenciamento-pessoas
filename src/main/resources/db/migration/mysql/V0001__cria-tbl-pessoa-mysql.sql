CREATE TABLE pessoa (
    id bigint NOT NULL auto_increment,
    nome VARCHAR(100),
    data_nascimento DATE,
    endereco_principal_id INT,
    primary key (id)
) engine=InnoDB default charset=utf8;