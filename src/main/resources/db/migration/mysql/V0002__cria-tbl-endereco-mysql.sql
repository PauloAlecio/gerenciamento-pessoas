CREATE TABLE endereco (
    id bigint NOT NULL auto_increment,
    logradouro VARCHAR(200) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    pessoa_id bigint,
    PRIMARY KEY (id)
) engine=InnoDB DEFAULT charset=utf8;