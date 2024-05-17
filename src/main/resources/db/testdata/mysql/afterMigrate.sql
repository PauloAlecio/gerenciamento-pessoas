DELETE FROM pessoa;
ALTER TABLE pessoa auto_increment = 1;

DELETE FROM endereco;
ALTER TABLE endereco auto_increment = 1;

	-- Inserir dados de exemplo na tabela Pessoa
INSERT INTO pessoa (nome, data_nascimento) VALUES
	('João Silva', '1990-05-15'),
	('Maria Oliveira', '1985-10-25'),
	('Carlos Santos', '1978-03-20');


	-- Inserir dados de exemplo na tabela Endereco
INSERT INTO endereco (logradouro, cep, numero, cidade, estado, pessoa_id) VALUES
	('Rua A', '12345-678', '100', 'São Paulo', 'SP', 1),
	('Rua B', '54321-876', '200', 'Rio de Janeiro', 'RJ', 2),
	('Avenida C', '98765-432', '300', 'Belo Horizonte', 'MG', 3);


