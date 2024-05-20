package com.pauloalecio.gerenciamentopessoas.api.v1.controller;

import com.pauloalecio.gerenciamentopessoas.api.ResourceUriHelper;
import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.EnderecoInputDisassembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.assembler.EnderecoModelAssembler;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.EnderecoModel;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInputId;
import com.pauloalecio.gerenciamentopessoas.domain.service.EnderecoService;
import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/enderecos")
public class EnderecoController {

  @Autowired
  private EnderecoModelAssembler enderecoModelAssembler;

  @Autowired
  private EnderecoInputDisassembler enderecoInputDisassembler;

  @Autowired
  private EnderecoService enderecoService;

  @GetMapping
  public ResponseEntity<CollectionModel<EnderecoModel>> listarEnderecos() {
    List<Endereco> enderecos = enderecoService.listarEnderecos();
    return ResponseEntity.ok(enderecoModelAssembler.toCollectionModel(enderecos));
  }

  @GetMapping("/{id}")
  public ResponseEntity<EnderecoModel> buscarEnderecoPorId(@PathVariable Long id) {
    Endereco endereco = enderecoService.buscarEnderecoPorId(id);
    return ResponseEntity.ok(enderecoModelAssembler.toModel(endereco));
  }

  @PostMapping
  public ResponseEntity<EnderecoModel> criarEndereco(@RequestBody @Valid EnderecoInput enderecoInput) {
    Endereco endereco = enderecoInputDisassembler.toDomainObject(enderecoInput);
    endereco = enderecoService.criarEndereco(endereco);
    var enderecoModel = enderecoModelAssembler.toModel(endereco);
    ResourceUriHelper.addUriInResponseHeader(enderecoModel.getId());
    return ResponseEntity.status(HttpStatus.CREATED).body(enderecoModel);
  }

  @PutMapping("/{id}")
  public ResponseEntity<EnderecoModel> atualizarEndereco(@PathVariable Long id,
      @RequestBody @Valid EnderecoInputId enderecoInputIdt) {
    Endereco enderecoAtual = enderecoService.buscarEnderecoPorId(id);
    enderecoInputDisassembler.copyToDomainObject(enderecoInputIdt,enderecoAtual);
    enderecoAtual =  enderecoService.atualizarEndereco(enderecoAtual);
    return ResponseEntity.ok(enderecoModelAssembler.toModel(enderecoAtual));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
    enderecoService.deletarEndereco(id);
    return ResponseEntity.noContent().build();
  }
}
