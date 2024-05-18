package com.pauloalecio.gerenciamentopessoas.api.v1.controller;


import com.pauloalecio.gerenciamentopessoas.api.ResourceUriHelper;
import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.EnderecoInputDisassembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.EnderecoModelAssembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.PessoaInputDisassembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.PessoaModelAssembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.EnderecoModel;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.PessoaModel;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInputId;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInputId;
import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import com.pauloalecio.gerenciamentopessoas.domain.service.EnderecoService;
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
  private EnderecoModelAssembler enderecoModelAssembler;

  @Autowired
  private EnderecoInputDisassembler enderecoInputDisassembler;

  @Autowired
  private EnderecoService enderecoService;

  @Autowired
  private PessoaService pessoaService;

  @GetMapping
  public ResponseEntity<CollectionModel<PessoaModel>> listarPessoas() {
    List<Pessoa> pessoas = pessoaService.listarPessoas();
    return ResponseEntity.ok(pessoModelAssembler.toCollectionModel(pessoas));
  }

  @GetMapping("/{pessoaId}")
  public ResponseEntity<PessoaModel> buscarPessoaPorId(@PathVariable Long pessoaId) {
    Pessoa pessoa = pessoaService.buscarPessoaPorId(pessoaId);
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

  @PutMapping("/{pessoaId}")
  public ResponseEntity<PessoaModel> atualizarPessoa(@PathVariable Long pessoaId, @RequestBody @Valid PessoaInputId pessoaInput) {
    Pessoa pessoaAtual = pessoaService.buscarPessoaPorId(pessoaId);
    pessoInputDisassembler.copyToDomainObject(pessoaInput,pessoaAtual);
    pessoaAtual = pessoaService.atualizarPessoa(pessoaAtual);
    return ResponseEntity.ok(pessoModelAssembler.toModel(pessoaAtual));
  }

  @PostMapping("/{pessoaId}/endereco")
  public ResponseEntity<EnderecoModel> adicionarEndereco(@PathVariable Long pessoaId, @RequestBody EnderecoInput enderecoInput) {
    Endereco endereco = enderecoInputDisassembler.toDomainObject(enderecoInput);
    endereco = pessoaService.adicionarEndereco(pessoaId, endereco);
    EnderecoModel enderecoModel = enderecoModelAssembler.toModel(endereco);
    ResourceUriHelper.addUriInResponseHeader(enderecoModel.getId());
    return new ResponseEntity<>(enderecoModel, HttpStatus.CREATED);
  }

  @PutMapping("/{pessoaId}/endereco/{enderecoId}")
  public ResponseEntity<EnderecoModel> editarEndereco(@PathVariable Long pessoaId,
      @PathVariable Long enderecoId, @RequestBody EnderecoInputId enderecoInputId) {
    Endereco enderecoAtual = enderecoService.buscarEnderecoPorId(enderecoId);
    enderecoInputDisassembler.copyToDomainObject(enderecoInputId,enderecoAtual);
    enderecoAtual = pessoaService.editarEndereco(pessoaId, enderecoAtual);
    return ResponseEntity.ok(enderecoModelAssembler.toModel(enderecoAtual));
  }

  @GetMapping("/{pessoaId}/enderecos")
  public ResponseEntity<CollectionModel<EnderecoModel>> buscarEnderecos(@PathVariable Long pessoaId) {
    List<Endereco> enderecos = enderecoService.buscarEndereco(pessoaId);
    return ResponseEntity.ok(enderecoModelAssembler.toCollectionModel(enderecos));
  }

  @GetMapping("/{pessoaId}/endereco/{enderecoId}")
  public ResponseEntity<EnderecoModel> buscarEnderecoPorPessoaId(@PathVariable Long pessoaId,
      @PathVariable Long enderecoId) {
    Endereco endereco = enderecoService.buscarEnderecoPorPessoaId(enderecoId,pessoaId);
    return ResponseEntity.ok(enderecoModelAssembler.toModel(endereco));
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

  @DeleteMapping("/{pessoaId}")
  public ResponseEntity<Void> deletarPessoa(@PathVariable Long pessoaId) {
    pessoaService.deletarPessoa(pessoaId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{pessoaId}/endereco/{enderecoId}")
  public ResponseEntity<Void> excluirEndereco(@PathVariable Long pessoaId, @PathVariable Long enderecoId) {
    pessoaService.excluirEndereco(pessoaId, enderecoId);
    return ResponseEntity.noContent().build();
  }
}
