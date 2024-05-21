package com.pauloalecio.gerenciamentopessoas.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PessoaInputId {

  @Schema(example = "1")
  @NotNull
  private Long id;

  @Schema(example = "João Paulo")
  @NotBlank
  private String nome;

  @Schema(example = "1985-03-04")
  @NotNull
  private LocalDate dataNascimento;

  @Schema(example = "1")
  @NotNull
  private Integer endereco_principal_id;

}
