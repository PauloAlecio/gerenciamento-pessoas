package com.pauloalecio.gerenciamentopessoas.api.v1.openapi;


import com.pauloalecio.gerenciamentopessoas.api.v1.model.EnderecoModel;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInputId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Endereços")
public interface EnderecoControllerOpenApi {

	@Operation(summary = "Lista os enderecos")
	ResponseEntity<CollectionModel<EnderecoModel>> listarEnderecos();


	@Operation(summary = "Busca uma endereco por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID da endereco inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Endereco não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<EnderecoModel> buscarEnderecoPorId(@Parameter(description = "ID da Endereco", example = "1", required = true) Long enderecoId);


	@Operation(summary = "Cadastra um endereco", responses = {
			@ApiResponse(responseCode = "201", description = "Endereco cadastrado"),
	})
	ResponseEntity<EnderecoModel> criarEndereco(
			@RequestBody(description = "Representação de um novo endereco", required = true) EnderecoInput enderecoInput);


	@Operation(summary = "Atualiza um endereco por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Endereco atualizado"),
			@ApiResponse(responseCode = "404", description = "Endereco não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<EnderecoModel> atualizarEndereco(
			@Parameter(description = "ID do endereco", example = "1", required = true) Long enderecoId,
			@RequestBody(description = "Representação de um endereco com os novos dados", required = true) EnderecoInputId enderecoInputId);


	@Operation(summary = "Excluir um endereco por ID",responses = {
			@ApiResponse(responseCode = "204"),
			@ApiResponse(responseCode = "400", description = "ID do endereco inválido",
					content = @Content(schema = @Schema(ref = "Problema"))
			),
			@ApiResponse(responseCode = "404", description = "endereco não encontrado",
					content = @Content(schema = @Schema(ref = "Problema"))
			)
	})
	ResponseEntity<Void> deletarEndereco(@Parameter(description = "ID do endereco", example = "1", required = true)Long enderecoId);





}

