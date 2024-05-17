package com.pauloalecio.gerenciamentopessoas.api.v1.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoInputId {

	@NotNull
	private Long id;

	@NotBlank
	private String cep;

	@NotBlank
	private String logradouro;

	@NotBlank
	private String numero;

	@NotBlank
	private String cidade;

	@NotBlank
	private String estado;


}
