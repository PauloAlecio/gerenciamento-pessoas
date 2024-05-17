package com.pauloalecio.gerenciamentopessoas.api.v1.model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "pessoas")
@Getter
@Setter
public class PessoaModel extends RepresentationModel<PessoaModel> {

  private Long id;

  private String nome;

  private LocalDate dataNascimento;

  private Integer endereco_principal_id;


}
