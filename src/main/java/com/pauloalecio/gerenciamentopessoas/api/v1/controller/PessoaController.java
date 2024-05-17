package com.pauloalecio.gerenciamentopessoas.api.v1.controller;


import com.pauloalecio.gerenciamentopessoas.api.ResourceUriHelper;
import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.PessoaInputDisassembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.PessoaModelAssembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.PessoaModel;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInputId;
import com.pauloalecio.gerenciamentopessoas.domain.service.PessoaService;
import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/pessoas")
public class PessoaController {

  @Autowired
  private PessoaModelAssembler pessoModelAssembler;

  @Autowired
  private PessoaInputDisassembler pessoInputDisassembler;

  @Autowired
  private PessoaService pessoaService;

  @GetMapping
  public ResponseEntity<CollectionModel<PessoaModel>> listarPessoas() {
    List<Pessoa> pessoas = pessoaService.listarPessoas();
    return ResponseEntity.ok(pessoModelAssembler.toCollectionModel(pessoas));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PessoaModel> buscarPessoaPorId(@PathVariable Long id) {
    Pessoa pessoa = pessoaService.buscarPessoaPorId(id);
    return ResponseEntity.ok(pessoModelAssembler.toModel(pessoa));
  }

  @PostMapping
  public ResponseEntity<PessoaModel> criarPessoa(@RequestBody @Valid PessoaInput pessoaInput) {
    Pessoa pessoa =  pessoInputDisassembler.toDomainObject(pessoaInput);
    pessoa = pessoaService.criarPessoa(pessoa);
    var pessoaModel = pessoModelAssembler.toModel(pessoa);
    ResourceUriHelper.addUriInResponseHeader(pessoaModel.getId());

    return ResponseEntity.status(HttpStatus.CREATED).body(pessoaModel);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PessoaModel> atualizarPessoa(@PathVariable Long id, @RequestBody @Valid PessoaInputId pessoaInput) {
    Pessoa pessoaAtual = pessoaService.buscarPessoaPorId(id);
    pessoInputDisassembler.copyToDomainObject(pessoaInput,pessoaAtual);
    pessoaAtual = pessoaService.atualizarPessoa(pessoaAtual);
    return ResponseEntity.ok(pessoModelAssembler.toModel(pessoaAtual));
  }

  @PostMapping("/{pessoaId}/endereco-principal")
  public ResponseEntity<Void> definirEnderecoPrincipal(@PathVariable Long pessoaId, @RequestParam(name = "enderecoId") Long enderecoId) {
    pessoaService.definirEnderecoPrincipal(pessoaId, enderecoId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{pessoaId}/endereco-principal")
  public ResponseEntity<Long> obterEnderecoPrincipal(@PathVariable Long pessoaId) {
    Long enderecoPrincipalId = pessoaService.obterEnderecoPrincipal(pessoaId);
    return ResponseEntity.ok(enderecoPrincipalId);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarPessoa(@PathVariable Long id) {
    pessoaService.deletarPessoa(id);
    return ResponseEntity.noContent().build();
  }
}
