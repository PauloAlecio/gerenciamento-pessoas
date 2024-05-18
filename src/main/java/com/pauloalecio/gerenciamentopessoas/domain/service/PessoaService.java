package com.pauloalecio.gerenciamentopessoas.domain.service;

import com.pauloalecio.gerenciamentopessoas.domain.exception.EnderecoNaoEncontradoException;
import com.pauloalecio.gerenciamentopessoas.domain.exception.PessoaNaoEncontradaException;
import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
import com.pauloalecio.gerenciamentopessoas.domain.repository.PessoaRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PessoaService {

  private final PessoaRepository pessoaRepository;
  private final EnderecoService enderecoService;

  public List<Pessoa> listarPessoas() {
    return pessoaRepository.findAll();
  }

  public Pessoa buscarPessoaPorId(Long id) {
    return pessoaRepository.findById(id).orElseThrow(() -> new PessoaNaoEncontradaException(id));
  }


  @Transactional
  public Pessoa criarPessoa(Pessoa pessoa) {
    return pessoaRepository.save(pessoa);
  }


  @Transactional
  public Pessoa atualizarPessoa(Pessoa pessoa) {
    return pessoaRepository.save(pessoa);
  }


  @Transactional
  public void deletarPessoa(Long id) {
    boolean exists = pessoaRepository.existsById(id);
    if (!exists){
      throw new PessoaNaoEncontradaException(id);
    }
    pessoaRepository.deleteById(id);
  }

  @Transactional
  public Endereco adicionarEndereco(Long pessoaId, Endereco endereco) {
        Pessoa pessoa = buscarPessoaPorId(pessoaId);
        endereco.setPessoa(pessoa);
        return enderecoService.criarEndereco(endereco);
  }

  @Transactional
  public Endereco editarEndereco(Long pessoaId, Endereco endereco) {
    Pessoa pessoa = buscarPessoaPorId(pessoaId);
    endereco.setPessoa(pessoa);
    return enderecoService.atualizarEndereco(endereco);
  }

  public void definirEnderecoPrincipal(Long pessoaId, Long enderecoId) {
    Pessoa pessoa = pessoaRepository.findById(pessoaId)
        .orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));
    Endereco endereco = enderecoService.buscarEnderecoPorId(enderecoId);

    if (!endereco.getPessoa().getId().equals(pessoaId)) {
      throw new EnderecoNaoEncontradoException("Endereço não pertence à pessoa especificada");
    }

    pessoa.setEndereco_principal_id(enderecoId);
    pessoaRepository.save(pessoa);
  }

  public void excluirEndereco(Long pessoaId, Long enderecoId) {
    Pessoa pessoa = pessoaRepository.findById(pessoaId)
        .orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));

    Endereco endereco = enderecoService.buscarEnderecoPorId(enderecoId);

    if (Objects.equals(pessoa.getEndereco_principal_id(), enderecoId)) {
      pessoa.setEndereco_principal_id(null);
      pessoaRepository.save(pessoa);
    }

    enderecoService.deletarEndereco(endereco.getId());
  }

  public Long obterEnderecoPrincipal(Long pessoaId) {
    Pessoa pessoa = pessoaRepository.findById(pessoaId).orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));
      return pessoa.getEndereco_principal_id();

  }
}