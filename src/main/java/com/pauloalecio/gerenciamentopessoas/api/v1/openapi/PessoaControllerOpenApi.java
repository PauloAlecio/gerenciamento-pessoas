package com.pauloalecio.gerenciamentopessoas.api.v1.openapi;


import com.pauloalecio.gerenciamentopessoas.api.v1.model.EnderecoModel;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.PessoaModel;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInputId;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInputId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pessoas")
public interface PessoaControllerOpenApi {

	@Operation(summary = "Lista as pessoas")
	ResponseEntity<CollectionModel<PessoaModel>> listarPessoas();


	@Operation(summary = "Busca uma pessoa por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID da pessoa inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Pessoas não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<PessoaModel> buscarPessoaPorId(@Parameter(description = "ID da Pessoas", example = "1", required = true) Long pessoaId);


	@Operation(summary = "Cadastra uma pessoas", responses = {
			@ApiResponse(responseCode = "201", description = "Pessoa cadastrada"),
	})
	ResponseEntity<PessoaModel> criarPessoa(
			@RequestBody(description = "Representação de uma nova pessoa", required = true) PessoaInput pessoaInput);


	@Operation(summary = "Atualiza uma pessoa por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Pessoa atualizado"),
			@ApiResponse(responseCode = "404", description = "Pessoa não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<PessoaModel> atualizarPessoa(
			@Parameter(description = "ID da pessoa", example = "1", required = true) Long pessoaId,
			@RequestBody(description = "Representação de uma pessoa com os novos dados", required = true) PessoaInputId pessoaInputId);


	@Operation(summary = "Excluir uma pessoa por ID",responses = {
			@ApiResponse(responseCode = "204"),
			@ApiResponse(responseCode = "400", description = "ID da pessoa inválido",
					content = @Content(schema = @Schema(ref = "Problema"))
			),
			@ApiResponse(responseCode = "404", description = "pessoa não encontrada",
					content = @Content(schema = @Schema(ref = "Problema"))
			)
	})
	ResponseEntity<Void> deletarPessoa(@Parameter(description = "ID de uma pessoa", example = "1", required = true)Long pessoaId);



	@Operation(summary = "Lista os enderecos de uma pessoa por ID ", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID da pessoa inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Pessoa não encontrada", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<CollectionModel<EnderecoModel>> buscarEnderecos(@Parameter(description = "ID da Pessoa", example = "1", required = true) Long pessoaId);


	@Operation(summary = "Busca um endereco por ID de Pessoa e ID de Endereço", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do endereco ou pessoa inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Endereco ou Pessoa não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<EnderecoModel> buscarEnderecoPorPessoaId(
			@Parameter(description = "ID da Pessoa", example = "1", required = true) Long pessoaId,
			@Parameter(description = "ID da Endereco", example = "1", required = true) Long enderecoId);


	@Operation(summary = "Cadastra um endereco para uma pessoaId", responses = {
			@ApiResponse(responseCode = "201", description = "Endereco cadastrado"),
			@ApiResponse(responseCode = "400", description = "ID da pessoa inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Pessoa não encontrada", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<EnderecoModel> adicionarEndereco(
			@Parameter(description = "ID da pessoa", example = "1", required = true) Long pessoaId,
			@RequestBody(description = "Representação de um novo endereco da pessoaId", required = true) EnderecoInput enderecoInput);


	@Operation(summary = "Atualiza um endereco por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Endereco atualizado"),
			@ApiResponse(responseCode = "400", description = "ID do endereco ou pessoa inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Endereco ou Pessoa não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<EnderecoModel> editarEndereco(
			@Parameter(description = "ID da pessoa", example = "1", required = true) Long pessoaId,
			@Parameter(description = "ID do endereco", example = "1", required = true) Long enderecoId,
			@RequestBody(description = "Representação de um endereco com os novos dados", required = true) EnderecoInputId enderecoInputId);


	@Operation(summary = "Excluir um endereco por Pessoa e Endereço ID" ,responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do endereco ou pessoa inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Endereco ou Pessoa não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<Void> excluirEndereco(
			@Parameter(description = "ID da Pessoa", example = "1", required = true) Long pessoaId,
			@Parameter(description = "ID da Endereco", example = "1", required = true) Long enderecoId);



	@Operation(summary = "Definir um endereco principal para uma Pessoa " ,responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do endereco ou pessoa inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Endereco ou Pessoa não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<Void> definirEnderecoPrincipal(
			@Parameter(description = "ID da Pessoa", example = "1", required = true) Long pessoaId,
			@Parameter(description = "ID da Endereco", example = "1", required = true) Long enderecoId);



	@Operation(summary = "Busca o endereco principal de uma Pessoa " ,responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID da pessoa inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Pessoa não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<Long> obterEnderecoPrincipal(
			@Parameter(description = "ID da Pessoa", example = "1", required = true) Long pessoaId);


}

