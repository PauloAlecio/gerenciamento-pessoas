CREATE TABLE pessoa (
    id bigint NOT NULL auto_increment,
    nome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    endereco_principal_id bigint,
    PRIMARY KEY (id)
) engine=InnoDB DEFAULT charset=utf8;