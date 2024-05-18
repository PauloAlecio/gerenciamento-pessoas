 ALTER TABLE endereco
       ADD CONSTRAINT fk_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)