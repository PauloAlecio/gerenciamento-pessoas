package com.pauloalecio.gerenciamentopessoas.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "pessoas")
@Getter
@Setter
public class PessoaModel extends RepresentationModel<PessoaModel> {

  @Schema(example = "1")
  private Long id;

  @Schema(example = "João da Silva")
  @NotBlank
  private String nome;

  @Schema(example = "1990-02-03")
  @NotNull
  private LocalDate dataNascimento;

  @Schema(example = "1")
  @NotNull
  private Integer endereco_principal_id;

}
