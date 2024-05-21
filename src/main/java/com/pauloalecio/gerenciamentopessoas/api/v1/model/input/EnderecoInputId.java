package com.pauloalecio.gerenciamentopessoas.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoInputId {

	@Schema(example = "1")
	@NotNull
	private Long id;

	@Schema(example = "89520-000")
	@NotBlank
	private String cep;

	@Schema(example = "Rua Marcondes Salgado")
	@NotBlank
	private String logradouro;

	@Schema(example = "\"2200\"")
	@NotBlank
	private String numero;

	@Schema(example = "Belo Horizonte")
	@NotBlank
	private String cidade;

	@Schema(example = "MG")
	@NotBlank
	private String estado;

}
