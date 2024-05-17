package com.pauloalecio.gerenciamentopessoas.domain.service;

import com.pauloalecio.gerenciamentopessoas.domain.exception.PessoaNaoEncontradaException;
import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
import com.pauloalecio.gerenciamentopessoas.domain.repository.PessoaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PessoaService {

  private final PessoaRepository pessoaRepository;

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

  public void definirEnderecoPrincipal(Long pessoaId, Long enderecoId) {
    Pessoa pessoa = pessoaRepository.findById(pessoaId).orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));
      pessoa.setEndereco_principal_id(enderecoId);
      pessoaRepository.save(pessoa);
  }

  public Long obterEnderecoPrincipal(Long pessoaId) {
    Pessoa pessoa = pessoaRepository.findById(pessoaId).orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));
      return pessoa.getEndereco_principal_id();

  }
}