package com.pauloalecio.gerenciamentopessoas.api.v1.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PessoaInputId {

  @NotNull
  private Long id;

  @NotBlank
  private String nome;

  @NotNull
  private LocalDate dataNascimento;

  @NotNull
  private Integer endereco_principal_id;

}
