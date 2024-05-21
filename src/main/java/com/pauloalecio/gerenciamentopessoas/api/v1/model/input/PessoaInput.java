package com.pauloalecio.gerenciamentopessoas.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PessoaInput {

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
