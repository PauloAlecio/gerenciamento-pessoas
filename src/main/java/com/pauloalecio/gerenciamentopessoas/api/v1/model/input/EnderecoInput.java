package com.pauloalecio.gerenciamentopessoas.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoInput {

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
