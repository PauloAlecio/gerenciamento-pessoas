package com.pauloalecio.gerenciamentopessoas.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "enderecos")
@Getter
@Setter
public class EnderecoModel extends RepresentationModel<EnderecoModel> {

  @Schema(example = "1")
  private Long id;

  @Schema(example = "38400-000")
  @NotBlank
  private String cep;

  @Schema(example = "Rua Floriano Peixoto")
  @NotBlank
  private String logradouro;

  @Schema(example = "\"1500\"")
  @NotBlank
  private String numero;

  @Schema(example = "Avaré")
  @NotBlank
  private String cidade;

  @Schema(example = "SP")
  @NotBlank
  private String estado;




}
