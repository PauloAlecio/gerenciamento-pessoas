package com.pauloalecio.gerenciamentopessoas.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "enderecos")
@Getter
@Setter
public class EnderecoModel extends RepresentationModel<EnderecoModel> {

  private Long id;

  private String logradouro;
  private String cep;
  private String numero;
  private String cidade;
  private String estado;



}
